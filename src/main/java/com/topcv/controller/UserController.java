package com.topcv.controller;

import com.topcv.model.Account;
import com.topcv.model.ResponseMessage;
import com.topcv.repository.IUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    final IUserRepository IUserRepository;
    final HttpSession Session;
    final HttpServletResponse Response;
    int userId;

    public UserController(com.topcv.repository.IUserRepository iUserRepository, HttpSession session, HttpServletResponse response) {
        IUserRepository = iUserRepository;
        Session = session;
        Response = response;
    }

    private boolean isLogged() {
        Account account = (Account) Session.getAttribute("account");
        if (account == null) {
            return false;
        }
        userId = account.getId();
        return true;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        if (!isLogged()) {
            return "redirect:/login";
        }

        Account account = IUserRepository.getAccountProfile(userId);
        model.addAttribute("userInformation", account);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(Account account, Model model) {
        if (!isLogged()) {
            return "redirect:/login";
        }
        ResponseMessage responseMessage = new ResponseMessage();

        boolean isEmailExist = IUserRepository.isEmailExist(account.getEmail(), userId);
        if (isEmailExist) {
            responseMessage.SetError("Email đã tồn tại.");
            model.addAttribute("responseMessage", responseMessage);
            return "profile";
        }

        account.setId(userId);
        int rowAffected = IUserRepository.updateProfile(account);
        if (rowAffected > 0) {
            responseMessage.SetSuccess("Cập nhật thông tin thành công.");
            model.addAttribute("responseMessage", responseMessage);
            Session.setAttribute("account", IUserRepository.getAccountProfile(userId));
            model.addAttribute("userInformation", IUserRepository.getAccountProfile(userId));
        } else {
            responseMessage.SetError("Cập nhật thông tin thất bại.");
            model.addAttribute("responseMessage", responseMessage);
        }
        return "profile";
    }
}
