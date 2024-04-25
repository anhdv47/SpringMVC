package com.topcv.repository;

import com.topcv.model.*;

public interface IUserRepository {

    Account login(Login login);

    int register(Register register);

    boolean isEmailExist(String email, int accountId);

    Account getAccountProfile(int id);

    Cv getCv(int accountId);

    int updateProfile(Account account);

    Company getCompanyProfile(int accountId);

    boolean updateCompanyLogo(int i, String base64Image);

    int updateCompany(Company company);

    boolean isCompanyExist(int accountId);

    int registerCompany(Company company);

    boolean updateAvatar(int i, String base64Image);
}
