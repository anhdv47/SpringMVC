package com.topcv.controller;

import com.topcv.model.Category;
import com.topcv.model.PagingModel;
import com.topcv.model.Recruitment;
import com.topcv.repository.ICategoryRepository;
import com.topcv.repository.IRecruitmentRepository;
import com.topcv.repository.IUserRepository;
import com.topcv.util.UserContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {
    final IRecruitmentRepository IRecruitmentRepository;
    final IUserRepository IUserRepository;
    final ICategoryRepository ICategoryRepository;
    final HttpSession Session;
    final HttpServletResponse Response;

    public RecruitmentController(IRecruitmentRepository iRecruitmentRepository, IUserRepository iUserRepository,
                                 ICategoryRepository iCategoryRepository, HttpSession session, HttpServletResponse response) {
        IRecruitmentRepository = iRecruitmentRepository;
        IUserRepository = iUserRepository;
        ICategoryRepository = iCategoryRepository;
        Session = session;
        Response = response;
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        PagingModel<Recruitment> recruitments = IRecruitmentRepository.list(UserContext.getCurrentCompany().getId(),
                page, size);
        if (recruitments.getData() == null) {
            recruitments.setData(new ArrayList<>());
        }
        model.addAttribute("recruitments", recruitments);
        return "post-list";
    }

    private void processRecruitment(Model model) {
        List<Category> categories = ICategoryRepository.GetAll();
        if (categories == null) {
            categories = new ArrayList<>();
        }
        model.addAttribute("categories", categories);
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("recruitment", new Recruitment());
        processRecruitment(model);
        return "post-job";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        Recruitment recruitment = IRecruitmentRepository.detail(id);
        if (recruitment == null) {
            return "redirect:/recruitment/list";
        }
        recruitment.setCompanyId(UserContext.getCurrentCompany().getId());
        model.addAttribute("recruitment", recruitment);
        processRecruitment(model);
        return "post-job";
    }

    @GetMapping("/detail-post/{id}")
    public String jobDetail(@PathVariable("id") int id, Model model) {
        Recruitment recruitment = IRecruitmentRepository.detail(id);
        if (recruitment == null) {
            return "redirect:/recruitment/list";
        }
        model.addAttribute("recruitment", recruitment);
        processRecruitment(model);
        return "detail-post";
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(Recruitment recruitment, Model model) {
        recruitment.setCompanyId(UserContext.getCurrentCompany().getId());
        int rowAffected = IRecruitmentRepository.add(recruitment);
        if (rowAffected > 0) {
            return ResponseEntity.ok().body("Thêm tin tuyển dụng thành công.");
        } else {
            return ResponseEntity.badRequest().body("Thêm tin tuyển dụng thất bại.");
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> updateProfile(Recruitment recruitment, Model model) {
        model.addAttribute("Content-Type", "text/html; charset=UTF-8");
        recruitment.setCompanyId(UserContext.getCurrentCompany().getId());
        int rowAffected = IRecruitmentRepository.update(recruitment);
        if (rowAffected > 0) {
            return ResponseEntity.ok().body("Cập nhật thông tin thành công.");
        } else {
            return ResponseEntity.badRequest().body("Cập nhật thông tin thất bại.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        int rowAffected = IRecruitmentRepository.delete(id, UserContext.getCurrentCompany().getId());
        if (rowAffected > 0) {
            return ResponseEntity.ok().body("Xóa tin tuyển dụng thành công.");
        } else {
            return ResponseEntity.badRequest().body("Xóa tin tuyển dụng thất bại.");
        }
    }
}
