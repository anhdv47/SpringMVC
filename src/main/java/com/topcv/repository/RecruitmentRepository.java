package com.topcv.repository;

import com.topcv.enumeration.CommonEnum.ApplyJobStatus;
import com.topcv.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

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
            return jdbcTemplate
                    .queryForObject(
                            "SELECT\n" + "  r.*\n" + " ,c.name AS companyName\n" + " ,c.logo AS companyLogo\n"
                                    + " ,c1.name AS categoryName\n" + "FROM recruitment r\n" + "LEFT JOIN company c\n"
                                    + "  ON r.companyId = c.id\n" + "LEFT JOIN category c1\n"
                                    + "  ON r.categoryId = c1.id\n" + "WHERE r.id = ?",
                            new BeanPropertyRowMapper<>(Recruitment.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int add(Recruitment recruitment) {
        try {
            return jdbcTemplate.update(
                    "INSERT INTO recruitment (address, description, experience, quantity, salary, status, title, type, deadline, companyId, categoryId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    recruitment.getAddress(), recruitment.getDescription(), recruitment.getExperience(),
                    recruitment.getQuantity(), recruitment.getSalary(), recruitment.getStatus(), recruitment.getTitle(),
                    recruitment.getType(), recruitment.getDeadline(), recruitment.getCompanyId(),
                    recruitment.getCategoryId());
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
            return jdbcTemplate.update(
                    "update recruitment set address = ?, description = ?, experience = ?, quantity = ?, salary = ?, title = ?, type = ?, deadline = ?, categoryId = ? where id = ? and companyId = ?",
                    recruitment.getAddress(), recruitment.getDescription(), recruitment.getExperience(),
                    recruitment.getQuantity(), recruitment.getSalary(), recruitment.getTitle(), recruitment.getType(),
                    recruitment.getDeadline(), recruitment.getCategoryId(), recruitment.getId(),
                    recruitment.getCompanyId());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    @Nullable
    public PagingModel<Recruitment> list(int companyId, int page, int size) {
        try {
            PagingModel<Recruitment> pagingModel = new PagingModel<>();
            List<Recruitment> recruitments = jdbcTemplate.query(
                    "select * FROM recruitment r WHERE r.companyId = ? order BY r.id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;",
                    new BeanPropertyRowMapper<>(Recruitment.class), companyId, (page - 1) * size, size);
            int totalItem = jdbcTemplate.queryForObject("select count(*) from recruitment where companyId = ?",
                    Integer.class, companyId);
            pagingModel.setData(recruitments);
            pagingModel.setTotalItem(totalItem);
            pagingModel.setTotalPage((int) Math.ceil((double) totalItem / size));
            pagingModel.setCurrentPage(page);
            return pagingModel;
        } catch (Exception e) {
            return null;
        }
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

    @Override
    public int applyCv(ApplyJob applyJob) {
        try {
            return jdbcTemplate.update(
                    "INSERT INTO apply_job (cvId, accountId, recruitmentId, status, note, createdAt) VALUES (?, ?, ?, ?, ?, GETDATE())",
                    applyJob.getCvId(), applyJob.getAccountId(), applyJob.getRecruitmentId(), applyJob.getStatus(),
                    applyJob.getNote());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public ResponseMessage cancelApply(int id, int accountId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            // lấy ra thông tin ứng tuyển
            ApplyJob applyJob = jdbcTemplate.queryForObject("SELECT * FROM apply_job WHERE id = ? AND accountId = ?",
                    new BeanPropertyRowMapper<>(ApplyJob.class), id, accountId);
            if (applyJob == null) {
                responseMessage.SetError("Không tìm thấy thông tin ứng tuyển.");
                return responseMessage;
            }

            // nếu trạng thái ứng tuyển đã được duyệt thì không thể hủy
            if (applyJob.getStatus() != ApplyJobStatus.PENDING.toInt()) {
                responseMessage.SetError("Không thể hủy ứng tuyển đã được duyệt.");
                return responseMessage;
            }

            int rowAffected = jdbcTemplate.update("DELETE FROM apply_job WHERE id = ? AND accountId = ?", id,
                    accountId);
            if (rowAffected > 0) {
                responseMessage.SetSuccess("Hủy ứng tuyển thành công.");
            } else {
                responseMessage.SetError("Hủy ứng tuyển thất bại.");
            }
        } catch (Exception e) {
            responseMessage.SetError("Hủy ứng tuyển thất bại.");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage deleteSaveJob(int id, int accountId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            int rowAffected = jdbcTemplate.update("DELETE FROM save_job WHERE id = ? AND accountId = ?", id,
                    accountId);
            if (rowAffected > 0) {
                responseMessage.SetSuccess("Xóa tin đã lưu thành công.");
            } else {
                responseMessage.SetError("Xóa tin đã lưu thất bại.");
            }
        } catch (Exception e) {
            responseMessage.SetError("Xóa tin đã lưu thất bại.");
        }
        return responseMessage;
    }
}
