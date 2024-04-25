package com.topcv.repository;

import com.topcv.model.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository implements ICategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> GetAll() {
        try {
            return jdbcTemplate.query("select * from category", new BeanPropertyRowMapper<>(Category.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
