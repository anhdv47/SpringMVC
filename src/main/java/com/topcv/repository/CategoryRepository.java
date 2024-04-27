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
            String sql = "SELECT\n" +
                    "  c.id\n" +
                    " ,c.name\n" +
                    " ,c.createdAt\n" +
                    " ,c.updatedAt\n" +
                    " ,COUNT(r.id) AS numberChoose\n" +
                    "FROM category c\n" +
                    "LEFT JOIN recruitment r\n" +
                    "  ON c.id = r.categoryId\n" +
                    "GROUP BY c.id\n" +
                    "        ,c.name\n" +
                    "        ,c.createdAt\n" +
                    "        ,c.updatedAt\n";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
