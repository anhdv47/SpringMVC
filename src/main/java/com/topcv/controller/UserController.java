package com.topcv.controller;

import com.topcv.model.Account;
import com.topcv.model.Company;
import com.topcv.model.ResponseMessage;
import com.topcv.repository.IUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private void processRequest(Model model) {
        Account account = IUserRepository.getAccountProfile(userId);
        model.addAttribute("userInformation", account);
        if (account.getRoleId() == 2) {
            Company company = IUserRepository.getCompanyProfile(userId);
            if (company == null) {
                company = new Company();
            }
            model.addAttribute("companyInformation", company);
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        if (!isLogged()) {
            return "redirect:/login";
        }

        processRequest(model);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Account account, Model model) {
        if (!isLogged()) {
            return "redirect:/login";
        }
        processRequest(model);

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


    @PostMapping("/update-company")
    public String updateCompany(@ModelAttribute Company company, Model model) {
        if (!isLogged()) {
            return "redirect:/login";
        }
        ResponseMessage responseMessage = new ResponseMessage();
        int rowAffected = 0;
        company.setAccountId(userId);
        if (company.getId() == 0) {
            rowAffected = IUserRepository.registerCompany(company);
        } else {
            rowAffected = IUserRepository.updateCompany(company);
        }
        String prefixMessage = company.getId() == 0 ? "Thêm" : "Cập nhật";
        if (rowAffected > 0) {
            responseMessage.SetSuccess(prefixMessage + " thông tin công ty thành công.");
        } else {
            responseMessage.SetError(prefixMessage + " thông tin công ty thất bại.");
        }
        model.addAttribute("responseMessage", responseMessage);
        processRequest(model);
        return "profile";
    }
}
