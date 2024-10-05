package com.topcv.repository;

import com.topcv.model.Category;
import com.topcv.model.Company;
import com.topcv.model.Recruitment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JobRepository implements IJobRepository {
    private final JdbcTemplate jdbcTemplate;

    public JobRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Nullable
    public Recruitment detail(int id) {
        return null;
    }

    @Override
    public int add(Recruitment recruitment) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(Recruitment recruitment) {
        return 0;
    }

    @Override
    @Nullable
    public List<Recruitment> list() {
        return jdbcTemplate.query("select * from recruitment", new RowMapper<Recruitment>() {
            @Override
            public Recruitment mapRow(ResultSet rs, int rownumber) throws SQLException {
                Recruitment r = new Recruitment();
                r.setId(rs.getInt("id"));
                r.setAddress(rs.getString("address"));
                r.setTitle(rs.getString("title"));
                r.setCompany(getCompany(rs.getInt("companyId")));
                r.setType(rs.getString("title"));
                return r;
            }
        });
    }

    @Override
    public Company getCompany(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM company WHERE id = ?",
                    new BeanPropertyRowMapper<>(Company.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Category getCategory(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM category WHERE id = ?",
                    new BeanPropertyRowMapper<>(Category.class), id);
        } catch (Exception e) {
            return null;
        }
    }
}
