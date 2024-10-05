package com.topcv.controller;

import com.topcv.enumeration.CommonEnum;
import com.topcv.model.*;
import com.topcv.repository.ICVRepository;
import com.topcv.repository.IRecruitmentRepository;
import com.topcv.repository.IUserRepository;
import com.topcv.util.UserContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    final IUserRepository IUserRepository;
    final HttpSession Session;
    final HttpServletResponse Response;
    final ICVRepository ICVRepository;
    final IRecruitmentRepository IRecruitmentRepository;

    public UserController(com.topcv.repository.IUserRepository iUserRepository, HttpSession session,
                          HttpServletResponse response, ICVRepository iCVRepository, IRecruitmentRepository iRecruitmentRepository) {
        IUserRepository = iUserRepository;
        Session = session;
        Response = response;
        ICVRepository = iCVRepository;
        IRecruitmentRepository = iRecruitmentRepository;
    }

    private void processRequest(Model model) {
        Account account = IUserRepository.getAccountProfile(UserContext.getCurrentUser().getId());
        model.addAttribute("userInformation", account);
        if (account.getRoleId() == CommonEnum.AccountRole.COMPANY.toInt()) {
            Company company = IUserRepository.getCompanyProfile(UserContext.getCurrentUser().getId());
            if (company == null) {
                company = new Company();
            }
            model.addAttribute("companyInformation", company);
        }
        if (account.getRoleId() == CommonEnum.AccountRole.USER.toInt()) {
            List<Cv> cvInformations = ICVRepository.getAllCv(UserContext.getCurrentUser().getId());
            model.addAttribute("cvInformations", cvInformations);
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        processRequest(model);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Account account, Model model) {
        processRequest(model);

        ResponseMessage responseMessage = new ResponseMessage();
        boolean isEmailExist = IUserRepository.isEmailExist(account.getEmail(), UserContext.getCurrentUser().getId());
        if (isEmailExist) {
            responseMessage.SetError("Email đã tồn tại.");
            model.addAttribute("responseMessage", responseMessage);
            return "profile";
        }

        account.setId(UserContext.getCurrentUser().getId());
        int rowAffected = IUserRepository.updateProfile(account);
        if (rowAffected > 0) {
            responseMessage.SetSuccess("Cập nhật thông tin thành công.");
            model.addAttribute("responseMessage", responseMessage);
            Session.setAttribute("account", IUserRepository.getAccountProfile(UserContext.getCurrentUser().getId()));
            model.addAttribute("userInformation",
                    IUserRepository.getAccountProfile(UserContext.getCurrentUser().getId()));
        } else {
            responseMessage.SetError("Cập nhật thông tin thất bại.");
            model.addAttribute("responseMessage", responseMessage);
        }

        return "profile";
    }

    @PostMapping("/update-company")
    public String updateCompany(@ModelAttribute Company company, Model model) {
        ResponseMessage responseMessage = new ResponseMessage();
        int rowAffected = 0;
        company.setAccountId(UserContext.getCurrentUser().getId());
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

    @GetMapping("/get-list-apply")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        PagingModel<ApplyJobDTO> applyJobs = IUserRepository.getListApply(UserContext.getCurrentUser().getId(),
                page, size);
        if (applyJobs.getData() == null) {
            applyJobs.setData(new ArrayList<>());
        }
        model.addAttribute("applyJobs", applyJobs);
        return "list-apply-job";
    }

    @GetMapping("/get-list-save-job")
    public String listSaveJob(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size) {
        PagingModel<SaveJobDTO> saveJobs = IUserRepository.getListSaveJob(UserContext.getCurrentUser().getId(),
                page, size);
        if (saveJobs.getData() == null) {
            saveJobs.setData(new ArrayList<>());
        }
        model.addAttribute("saveJobs", saveJobs);
        return "list-save-job";
    }

    @PostMapping("/save-job")
    @ResponseBody
    public ResponseMessage saveJob(@RequestParam int recruimentId) {
        ResponseMessage responseMessage = IUserRepository.saveJob(recruimentId, UserContext.getCurrentUser().getId());
        return responseMessage;
    }

    @DeleteMapping("/cancel-apply/{id}")
    @ResponseBody
    public ResponseMessage cancelApply(@PathVariable int id) {
        ResponseMessage result = IRecruitmentRepository.cancelApply(id, UserContext.getCurrentUser().getId());
        return result;
    }

    @DeleteMapping("/delete-save-job/{id}")
    @ResponseBody
    public ResponseMessage deleteSaveJob(@PathVariable int id) {
        ResponseMessage result = IRecruitmentRepository.deleteSaveJob(id, UserContext.getCurrentUser().getId());
        return result;
    }

    @GetMapping("/get-list-cv")
    @ResponseBody
    public List<Cv> getCv() {
        return ICVRepository.getAllCv(UserContext.getCurrentUser().getId());
    }

    @GetMapping("/get-cv/{cvId}")
    @ResponseBody
    public Cv getCv(@PathVariable int cvId) {
        return ICVRepository.getCv(cvId, UserContext.getCurrentUser().getId(), false);
    }

    @DeleteMapping("/delete-cv/{cvId}")
    @ResponseBody
    public ResponseMessage deleteCv(@PathVariable int cvId) {
        ResponseMessage responseMessage = new ResponseMessage();

        // lấy ra thông tin cv
        Cv cv = ICVRepository.getCv(cvId, UserContext.getCurrentUser().getId(), false);
        if (cv == null) {
            responseMessage.SetError("CV không tồn tại.");
            return responseMessage;
        }
        boolean isCVApplied = ICVRepository.isCvApplied(cvId, UserContext.getCurrentUser().getId(), 0);
        if (isCVApplied) {
            responseMessage.SetError("CV đã được ứng tuyển, không thể xóa.");
            return responseMessage;
        }
        int rowAffected = ICVRepository.delete(cvId);
        if (rowAffected > 0) {
            responseMessage.SetSuccess("Xóa CV thành công.");
            File fileToDelete = new File("uploads/" + cv.getFilePath());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        } else {
            responseMessage.SetError("Xóa CV thất bại.");
        }
        return responseMessage;
    }

    @PostMapping("/save-cv")
    @ResponseBody
    public ResponseEntity<ResponseMessage> saveCv(@RequestParam("id") int id, @RequestParam("name") String name,
                                                  @RequestParam("action") String action, @RequestParam("file") MultipartFile file) {

        ResponseMessage responseMessage = new ResponseMessage();

        if (Objects.equals(action, "add") && file.isEmpty()) {
            responseMessage.setMessage("Vui lòng chọn file CV.");
            return ResponseEntity.badRequest().body(responseMessage);
        }

        String fileName = null;

        if (!file.isEmpty()) {
            try {
                // Lấy đường dẫn tuyệt đối của thư mục uploads
                String uploadDir = new File("uploads").getAbsolutePath(); // Thay đổi nếu cần
                File uploadPath = new File(uploadDir);

                // Tạo thư mục nếu chưa tồn tại
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File fileToSave = new File(uploadPath, fileName);
                file.transferTo(fileToSave); // Lưu file
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(responseMessage);
            }
        }

        if (Objects.equals(action, "add")) {
            Cv cv = new Cv();
            cv.setAccountId(UserContext.getCurrentUser().getId());
            cv.setName(name);
            cv.setFilePath(fileName);
            cv.setStatus(CommonEnum.CvStatus.ACTIVE.toInt());
            int rowAffected = ICVRepository.insert(cv);
            if (rowAffected > 0) {
                responseMessage.SetSuccess("Thêm CV thành công.");
            } else {
                File fileToDelete = new File("uploads/" + fileName);
                if (fileToDelete.exists()) {
                    fileToDelete.delete();
                }
                responseMessage.SetError("Thêm CV thất bại.");
            }
        } else {
            Cv cv = ICVRepository.getCv(id, UserContext.getCurrentUser().getId(), false);
            if (cv == null) {
                responseMessage.SetError("CV không tồn tại.");
                return ResponseEntity.badRequest().body(responseMessage);
            }
            String oldFileName = cv.getFilePath();
            if (fileName != null && !fileName.equals(cv.getFilePath())) {
                cv.setFilePath(fileName);
            }
            cv.setName(name);
            cv.setStatus(CommonEnum.CvStatus.ACTIVE.toInt());
            int rowAffected = ICVRepository.update(cv);
            if (rowAffected > 0) {
                responseMessage.SetSuccess("Cập nhật CV thành công.");
                if (fileName != null && !fileName.equals(oldFileName)) {
                    File fileToDelete = new File("uploads/" + oldFileName);
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                    }
                }
            } else {
                responseMessage.SetError("Cập nhật CV thất bại.");
                if (fileName != null) {
                    File fileToDelete = new File("uploads/" + fileName);
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                    }
                }
            }
        }
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/download-cv/{cvId}")
    public void downloadCv(@PathVariable int cvId) {
        Cv cv = ICVRepository.getCv(cvId, UserContext.getCurrentUser().getId(), false);
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

    // api ứng tuyển cv
    @PostMapping("/apply-cv")
    @ResponseBody
    public ResponseMessage applyCv(@RequestParam int cvId, @RequestParam int recruimentId, @RequestParam String note) {
        ResponseMessage responseMessage = new ResponseMessage();
        boolean isCvApplied = ICVRepository.isCvApplied(cvId, UserContext.getCurrentUser().getId(), recruimentId);
        if (isCvApplied) {
            responseMessage.SetError("CV đã được ứng tuyển.");
            return responseMessage;
        }
        Recruitment recruitment = IRecruitmentRepository.detail(recruimentId);
        if (recruitment == null) {
            responseMessage.SetError("Tin tuyển dụng không tồn tại.");
            return responseMessage;
        }
        ApplyJob applyJob = new ApplyJob();
        applyJob.setAccountId(UserContext.getCurrentUser().getId());
        applyJob.setCvId(cvId);
        applyJob.setRecruitmentId(recruimentId);
        applyJob.setNote(note);
        applyJob.setStatus(CommonEnum.ApplyJobStatus.PENDING.toInt());
        int rowAffected = IRecruitmentRepository.applyCv(applyJob);
        if (rowAffected > 0) {
            responseMessage.SetSuccess("Ứng tuyển CV thành công.");
        } else {
            responseMessage.SetError("Ứng tuyển CV thất bại.");
        }
        return responseMessage;
    }
}
