package com.topcv.controller;

import com.topcv.enumeration.CommonEnum.ApplyJobStatus;
import com.topcv.model.*;
import com.topcv.repository.*;
import com.topcv.util.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/employer")
public class EmployerController {
    final IRecruitmentRepository IRecruitmentRepository;
    final IUserRepository IUserRepository;
    final ICategoryRepository ICategoryRepository;
    final HttpSession Session;
    final HttpServletResponse Response;
    final IEmployerRepository IEmployerRepository;
    final ICVRepository ICVRepository;

    public EmployerController(IRecruitmentRepository iRecruitmentRepository, IUserRepository iUserRepository,
                              ICategoryRepository iCategoryRepository, HttpSession session, HttpServletResponse response,
                              IEmployerRepository iEmployerRepository, ICVRepository iCVRepository) {
        IRecruitmentRepository = iRecruitmentRepository;
        IUserRepository = iUserRepository;
        ICategoryRepository = iCategoryRepository;
        Session = session;
        Response = response;
        IEmployerRepository = iEmployerRepository;
        ICVRepository = iCVRepository;
    }

    @GetMapping("/list-cv")
    public String listCV(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int size) {
        PagingModel<ApplyJobCompanyDTO> applyJobs = IEmployerRepository.getListApplyByCompany(
                UserContext.getCurrentCompany().getId(),
                page, size);
        if (applyJobs.getData() == null) {
            applyJobs.setData(new ArrayList<>());
        }
        model.addAttribute("applyJobs", applyJobs);
        return "list-apply-job-by-company";
    }

    @GetMapping("/list-cv/{id}")
    @ResponseBody
    public ArrayList<ApplyJobCompanyDetailDTO> listCV(@PathVariable("id") int id) {
        return IEmployerRepository.getDetailApplyByCompany(id);
    }

    @PostMapping("/approve-cv/{cvId}")
    @ResponseBody
    public ResponseMessage approveCv(@PathVariable int cvId) {
        return IEmployerRepository.updateCvStatus(cvId, UserContext.getCurrentCompany().getId(),
                ApplyJobStatus.ACCEPTED.toInt());
    }

    @PostMapping("/reject-cv/{cvId}")
    @ResponseBody
    public ResponseMessage rejectCv(@PathVariable int cvId) {
        return IEmployerRepository.updateCvStatus(cvId, UserContext.getCurrentCompany().getId(),
                ApplyJobStatus.REJECTED.toInt());
    }

    @GetMapping("/download-cv/{cvId}")
    public void downloadCv(@PathVariable int cvId) {
        Cv cv = ICVRepository.getCv(cvId, 0, true);
        if (cv == null) {
            return;
        }
        File file = new File("uploads/" + cv.getFilePath());
        if (file.exists()) {
            Response.setContentType("application/octet-stream");
            Response.setHeader("Content-Disposition", "attachment; filename=" + cv.getFilePath());
            try {
                org.apache.commons.io.FileUtils.copyFile(file, Response.getOutputStream());
                Response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
