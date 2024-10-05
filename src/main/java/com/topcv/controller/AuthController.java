package com.topcv.controller;

import com.topcv.enumeration.CommonEnum;
import com.topcv.model.*;
import com.topcv.repository.IUserRepository;
import com.topcv.util.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    final IUserRepository IUserRepository;

    public AuthController(IUserRepository IUserRepository) {
        this.IUserRepository = IUserRepository;
    }

    @GetMapping("/login")
    public String login(ModelMap modelMap) {
        modelMap.addAttribute("register", new Register());
        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(String email, String password, HttpSession session, Model model) {
        ResponseMessage responseMessage = new ResponseMessage();

        Login login = new Login(email, password);
        Account account = IUserRepository.login(login);
        model.addAttribute("register", new Register());
        if (account != null) {
            session.setAttribute("account", account);
            UserContext.setCurrentUser(account);
            if (account.getRoleId() == CommonEnum.AccountRole.COMPANY.toInt()) {
                Company company = IUserRepository.getCompanyProfile(account.getId());
                if (company != null) {
                    UserContext.setCurrentCompany(company);
                    session.setAttribute("sessionCompany", company);
                    session.setAttribute("company", company);
                }
            }
            return "redirect:/";
        } else {
            responseMessage.SetError("Email hoặc mật khẩu không đúng.");
            model.addAttribute("responseMessage", responseMessage);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("account");
        return "redirect:/login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Register register, Model model) {
        model.addAttribute("Content-Type", "text/html; charset=UTF-8");
        ResponseMessage responseMessage = new ResponseMessage();
        model.addAttribute("register", register);
        if (!register.getPassword().equals(register.getConfirmPassword())) {
            responseMessage.SetError("Mật khẩu không khớp.");
            model.addAttribute("responseMessage", responseMessage);
            return "login";
        }
        if (IUserRepository.isEmailExist(register.getEmail(), 0)) {
            responseMessage.SetError("Email đã tồn tại.");
            model.addAttribute("responseMessage", responseMessage);
            return "login";
        }
        int rowExecuted = IUserRepository.register(register);
        if (rowExecuted > 0) {
            responseMessage.SetSuccess("Đăng ký thành công, vui lòng đăng nhập.");
            model.addAttribute("responseMessage", responseMessage);
        } else {
            responseMessage.SetError("Đăng ký thất bại, vui lòng thử lại.");
            model.addAttribute("responseMessage", responseMessage);
        }
        return "login";
    }
}
