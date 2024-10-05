package com.topcv.repository;

import com.topcv.model.Cv;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CVRepository implements ICVRepository {
    private final JdbcTemplate jdbcTemplate;

    public CVRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cv getCv(int cvId, int accountId, boolean isEmployer) {
        try {
            // neu la employer thi lay cv cua ung vien tư apply_job
            if (isEmployer) {
                String query = "SELECT\r\n" + //
                        "  c.*\r\n" + //
                        "FROM apply_job aj\r\n" + //
                        "JOIN cv c\r\n" + //
                        "  ON aj.cvId = c.id\r\n" + //
                        "WHERE aj.id = ?";
                return jdbcTemplate.queryForObject(query,
                        new BeanPropertyRowMapper<>(Cv.class), cvId);
            }
            return jdbcTemplate.queryForObject("SELECT * FROM cv WHERE accountId = ? AND id = ?",
                    new BeanPropertyRowMapper<>(Cv.class), accountId, cvId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int insert(Cv cv) {
        try {
            return jdbcTemplate.update(
                    "INSERT INTO cv(accountId, name, filePath, status, createdAt, updatedAt) VALUES (?, ?, ?, ?, GETDATE(), GETDATE())",
                    cv.getAccountId(), cv.getName(), cv.getFilePath(), cv.getStatus());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int update(Cv cv) {
        try {
            return jdbcTemplate.update(
                    "UPDATE cv SET name = ?, filePath = ?, status = ?, updatedAt = GETDATE() WHERE id = ? and accountId = ?",
                    cv.getName(), cv.getFilePath(), cv.getStatus(), cv.getId(), cv.getAccountId());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        try {
            return jdbcTemplate.update("DELETE FROM cv WHERE id = ?", id);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<Cv> getAllCv(int accountId) {
        try {
            return jdbcTemplate.query("SELECT * FROM cv WHERE accountId = ?", new BeanPropertyRowMapper<>(Cv.class),
                    accountId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isCvApplied(int cvId, int accountId, int recruitmentId) {
        try {
            if (recruitmentId > 0) {
                return jdbcTemplate.queryForObject(
                        "SELECT COUNT(1) FROM apply_job WHERE cvId = ? AND accountId = ? AND recruitmentId = ?",
                        Integer.class, cvId, accountId, recruitmentId) > 0;
            }
            return jdbcTemplate.queryForObject(
                    "SELECT COUNT(1) FROM apply_job WHERE cvId = ? AND accountId = ?",
                    Integer.class, cvId, accountId) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
