package com.topcv.repository;

import com.topcv.enumeration.CommonEnum;
import com.topcv.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class UserRepository implements IUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Nullable
    public Account login(Login login) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM account WHERE email = ? AND password = ?", new BeanPropertyRowMapper<>(Account.class), login.getEmail(), login.getPassword());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int register(Register register) {
        try {
            return jdbcTemplate.update("INSERT INTO account(email, password, fullName, roleId, status) VALUES (?, ?, ?, ?, ?)", register.getEmail(), register.getPassword(), register.getFullName(), register.getRole(), CommonEnum.AccountStatus.ACTIVE.toInt());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean isEmailExist(String email, int accountId) {
        try {
            if (accountId > 0) {
                return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM account WHERE email = ? AND id != ?", Integer.class, email, accountId) > 0;
            }
            return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM account WHERE email = ?", Integer.class, email) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Account getAccountProfile(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM account WHERE id = ?", new BeanPropertyRowMapper<>(Account.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Cv getCv(int accountId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM cv WHERE accountId = ?", new BeanPropertyRowMapper<>(Cv.class), accountId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int updateProfile(Account account) {
        try {
            return jdbcTemplate.update("UPDATE account SET address = ?, description = ?, email = ?, fullName = ?, phoneNumber = ? WHERE id = ?", account.getAddress(), account.getDescription(), account.getEmail(), account.getFullName(), account.getPhoneNumber(), account.getId());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Company getCompanyProfile(int accountId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM company WHERE accountId = ?", new BeanPropertyRowMapper<>(Company.class), accountId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateCompanyLogo(int i, String base64Image) {
        try {
            return jdbcTemplate.update("UPDATE company SET logo = ? WHERE accountId = ?", base64Image, i) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int updateCompany(Company company) {
        try {
            return jdbcTemplate.update("UPDATE company SET address = ?, description = ?, email = ?, name = ?, phoneNumber = ? WHERE accountId = ?", company.getAddress(), company.getDescription(), company.getEmail(), company.getName(), company.getPhoneNumber(), company.getAccountId());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean isCompanyExist(int accountId) {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM company WHERE accountId = ?", Integer.class, accountId) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int registerCompany(Company company) {
        try {
            return jdbcTemplate.update("INSERT INTO company(accountId, address, description, email, name, phoneNumber, status) VALUES (?, ?, ?, ?, ?, ?, ?)", company.getAccountId(), company.getAddress(), company.getDescription(), company.getEmail(), company.getName(), company.getPhoneNumber(), CommonEnum.CompanyStatus.ACTIVE.toInt());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean updateAvatar(int i, String base64Image) {
        try {
            return jdbcTemplate.update("UPDATE account SET image = ? WHERE id = ?", base64Image, i) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
