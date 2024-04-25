package com.topcv.interceptor;

import com.topcv.model.Account;
import com.topcv.model.Company;
import com.topcv.model.Login;
import com.topcv.repository.IUserRepository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AuthInterceptor implements HandlerInterceptor {

    final IUserRepository IUserRepository;

    public AuthInterceptor(com.topcv.repository.IUserRepository iUserRepository) {
        IUserRepository = iUserRepository;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("account") == null) {
            Account acc = IUserRepository.login(new Login("vietdang0407@gmail.com", "123123"));
            session.setAttribute("account", acc);
            if (acc.getRoleId() == 2) {
                session.setAttribute("company", IUserRepository.getCompanyProfile(acc.getId()));
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Code xử lý sau khi controller xử lý request.
        String uri = request.getRequestURI();
        System.out.println("URI: " + uri);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Code xử lý sau khi response đã được gửi đến client.
        String uri = request.getRequestURI();
    }
}
