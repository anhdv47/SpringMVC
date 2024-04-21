package com.topcv.repository;

import com.topcv.model.Account;
import com.topcv.model.Login;
import com.topcv.model.Register;
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
    public Account register(Register register) {
        try {
            jdbcTemplate.update("INSERT INTO account(email, password, full_name, role_id) VALUES (?, ?, ?, ?)", register.getEmail(), register.getPassword(), register.getFullName(), register.getRole());
            return jdbcTemplate.queryForObject("SELECT * FROM account WHERE email = ? AND password = ?", new BeanPropertyRowMapper<>(Account.class), register.getEmail(), register.getPassword());
        } catch (Exception e) {
            return null;
        }
    }
}
