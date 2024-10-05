package com.topcv.util;

import com.topcv.model.Account;
import com.topcv.model.Company;

public class UserContext {

    private static ThreadLocal<Account> currentUser = new ThreadLocal<>();
    private static ThreadLocal<Company> currentCompany = new ThreadLocal<>();

    public static void setCurrentUser(Account account) {
        currentUser.set(account);
    }

    public static Account getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentCompany(Company company) {
        currentCompany.set(company);
    }

    public static Company getCurrentCompany() {
        return currentCompany.get();
    }

    public static void clear() {
        currentUser.remove();
        currentCompany.remove();
    }
}
