package com.topcv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    //    @GetMapping("/")
    //    public String testModel(Model model, ModelMap modelMap) {
    //        model.addAttribute("model", "Hello Model");
    //        modelMap.addAttribute("modelMap", "Hello modelMap");
    //        return "login";
    //    }
}