package com.topcv.repository;

import com.topcv.model.Account;
import com.topcv.model.Login;
import com.topcv.model.Register;

public interface IUserRepository {

    Account login(Login login);

    Account register(Register register);
}
