package com.topcv.controller;


import com.topcv.repository.IUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import javax.servlet.http.HttpSession;

/**
 * UploadController
 * Dùng để xử lý các request liên quan đến việc upload file
 * thông qua api
 */

@Controller
@RequestMapping("/upload")
public class UploadController {

    final HttpSession Session;
    final IUserRepository IUserRepository;

    public UploadController(HttpSession session, IUserRepository IUserRepository) {
        Session = session;
        this.IUserRepository = IUserRepository;
    }

    @PostMapping("/file")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("action") String action) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select an file to upload");
        }

        // check nếu file có type image hay không
        if (!file.getContentType().contains("image")) {
            return ResponseEntity.badRequest().body("Please select an image file to upload");
        }

        // Chuyển đổi và lưu file dưới dạng base64
        try {
            String fileName = file.getOriginalFilename();
            String base64Image = convertToBase64(file.getBytes());
            boolean updated = false;

            if (action.equals("companyLogo")) {
                updated = IUserRepository.updateCompanyLogo(1, base64Image);
                if (updated) {
                    assert fileName != null;
                    return ResponseEntity.ok().body("data:image/" + fileName.split("\\.")[1] + ";base64," + base64Image);
                }
            }

            if (action.equals("avatar")) {
                updated = IUserRepository.updateAvatar(1, base64Image);
                if (updated) {
                    assert fileName != null;
                    return ResponseEntity.ok().body("data:image/" + fileName.split("\\.")[1] + ";base64," + base64Image);
                }
            }
            return ResponseEntity.status(500).body("Failed to upload file");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload file");
        }
    }

    // Chuyển đổi mảng byte thành chuỗi base64
    private String convertToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
