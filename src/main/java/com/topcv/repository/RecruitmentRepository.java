package com.topcv.repository;

import com.topcv.enumeration.CommonEnum;
import com.topcv.model.Account;
import com.topcv.model.Category;
import com.topcv.model.Company;
import com.topcv.model.Recruitment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RecruitmentRepository implements IRecruitmentRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecruitmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Nullable
    public Recruitment detail(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM recruitment WHERE id = ?", new BeanPropertyRowMapper<>(Recruitment.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int add(Recruitment recruitment) {
        try {
            return jdbcTemplate.update("INSERT INTO recruitment (address, description, experience, quantity, salary, status, title, type, deadline, companyId, categoryId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", recruitment.getAddress(), recruitment.getDescription(), recruitment.getExperience(), recruitment.getQuantity(), recruitment.getSalary(), recruitment.getStatus(), recruitment.getTitle(), recruitment.getType(), recruitment.getDeadline(), recruitment.getCompanyId(), recruitment.getCategoryId());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int delete(int id, int companyId) {
        try {
            return jdbcTemplate.update("delete from recruitment where id = ? and companyId = ?", id, companyId);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int update(Recruitment recruitment) {
        try {
            return jdbcTemplate.update("update recruitment set address = ?, description = ?, experience = ?, quantity = ?, salary = ?, title = ?, type = ?, deadline = ?, categoryId = ? where id = ? and companyId = ?", recruitment.getAddress(), recruitment.getDescription(), recruitment.getExperience(), recruitment.getQuantity(), recruitment.getSalary(), recruitment.getTitle(), recruitment.getType(), recruitment.getDeadline(), recruitment.getCategoryId(), recruitment.getId(), recruitment.getCompanyId());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    @Nullable
    public List<Recruitment> list(int companyId) {
        try {
            return jdbcTemplate.query("select * from recruitment where companyId = ?", new BeanPropertyRowMapper<>(Recruitment.class), companyId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Company getCompany(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM company WHERE id = ?", new BeanPropertyRowMapper<>(Company.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Category getCategory(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM category WHERE id = ?", new BeanPropertyRowMapper<>(Category.class), id);
        } catch (Exception e) {
            return null;
        }
    }
}
