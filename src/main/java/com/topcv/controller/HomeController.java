package com.topcv.controller;

import com.topcv.model.*;
import com.topcv.repository.ICategoryRepository;
import com.topcv.repository.IHomeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    IHomeRepository homeRepository;
    ICategoryRepository categoryRepository;

    public HomeController(IHomeRepository homeRepository, ICategoryRepository categoryRepository) {
        this.homeRepository = homeRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        HomeCount homeCount = homeRepository.getHomeCount();
        List<Category> categories = categoryRepository.GetAll();
        PagingModel<Recruitment> recruitmentPagingModel = homeRepository.getAllRecruitment(1, 20);
        PagingModel<Company> companyPagingModel = homeRepository.getAllCompany(1, 20);
        model.addAttribute("homeCount", homeCount);
        model.addAttribute("categories", categories);
        model.addAttribute("recruitments", recruitmentPagingModel.getData());
        model.addAttribute("companies", companyPagingModel.getData());
        return "home";
    }
}