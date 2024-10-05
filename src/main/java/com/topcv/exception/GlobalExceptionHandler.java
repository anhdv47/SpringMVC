package com.topcv.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException ex) {
        // Kiểm tra ngoại lệ NullPointerException có liên quan đến UserContext
        if (ex.getMessage().contains("UserContext.getCurrentUser()") || ex.getMessage().contains("UserContext.getCurrentCompany()")) {
            return "redirect:/login";  // Điều hướng về trang đăng nhập
        }
        // Xử lý các trường hợp khác của NullPointerException nếu cần
        return "error";
    }
}
