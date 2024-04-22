package com.topcv.repository;

import com.topcv.model.Account;
import com.topcv.model.Cv;
import com.topcv.model.Login;
import com.topcv.model.Register;

public interface IUserRepository {

    Account login(Login login);

    int register(Register register);

    boolean isEmailExist(String email, int accountId);

    Account getAccountProfile(int id);

    Cv getCv(int accountId);

    int updateProfile(Account account);
}
