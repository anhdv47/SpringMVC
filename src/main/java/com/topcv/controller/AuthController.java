package com.topcv.controller;

import com.topcv.model.Account;
import com.topcv.model.Login;
import com.topcv.model.Register;
import com.topcv.repository.IUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/login")
    public ModelAndView login(String email, String password) {
        Login login = new Login(email, password);
        Account account = IUserRepository.login(login);
        if (account != null) {
            return new ModelAndView("home");
        } else {
            return new ModelAndView("login");
        }
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute Register register) {
        Account account = IUserRepository.register(register);
        if (account != null) {
            return new ModelAndView("home");
        } else {
            return new ModelAndView("register");
        }
    }

}
