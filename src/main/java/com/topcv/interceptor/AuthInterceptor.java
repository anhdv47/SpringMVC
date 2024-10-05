package com.topcv.interceptor;

import com.topcv.model.Account;
import com.topcv.model.Company;
import com.topcv.repository.IUserRepository;
import com.topcv.util.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    final IUserRepository IUserRepository;

    public AuthInterceptor(IUserRepository iUserRepository) {
        IUserRepository = iUserRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        Company company = (Company) session.getAttribute("company");
        if (acc != null) {
            UserContext.setCurrentUser(acc);
        }
        if (company != null) {
            UserContext.setCurrentCompany(company);
        }
//        if (acc == null) {
//            acc = IUserRepository.login(new Login("nguyet@gmail.com", "12345678"));
//            session.setAttribute("account", acc);
//            if (acc.getRoleId() == 2) {
//                session.setAttribute("company", IUserRepository.getCompanyProfile(acc.getId()));
//            }
//        }
//        UserContext.setCurrentUser(acc);
//        if (acc.getRoleId() == 2) {
//            UserContext.setCurrentCompany(IUserRepository.getCompanyProfile(acc.getId()));
//        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Xóa thông tin người dùng sau khi xử lý request xong
        UserContext.clear();
    }
}
