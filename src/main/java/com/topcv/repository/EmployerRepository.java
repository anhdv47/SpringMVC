package com.topcv.repository;

import com.topcv.enumeration.CommonEnum.ApplyJobStatus;
import com.topcv.model.ApplyJobCompanyDTO;
import com.topcv.model.ApplyJobCompanyDetailDTO;
import com.topcv.model.PagingModel;
import com.topcv.model.ResponseMessage;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployerRepository implements IEmployerRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Nullable
    public PagingModel<ApplyJobCompanyDTO> getListApplyByCompany(int companyId, int page, int size) {
        try {
            PagingModel<ApplyJobCompanyDTO> pagingModel = new PagingModel<>();
            String query = "SELECT\r\n" + //
                    "  r.id\r\n" + //
                    " ,r.title\r\n" + //
                    " ,r.createdAt\r\n" + //
                    " ,r.companyId\r\n" + //
                    " ,r.address\r\n" + //
                    " ,r.type\r\n" + //
                    " ,r.deadline\r\n" + //
                    " ,COUNT(aj.accountId) AS totalApplied\r\n" + //
                    "FROM recruitment r\r\n" + //
                    "JOIN company c\r\n" + //
                    "  ON r.companyId = c.id\r\n" + //
                    "LEFT JOIN apply_job aj\r\n" + //
                    "  ON r.id = aj.recruitmentId\r\n" + //
                    "WHERE r.companyId = ?\r\n" + //
                    "GROUP BY r.id\r\n" + //
                    "        ,r.title\r\n" + //
                    "        ,r.createdAt\r\n" + //
                    "        ,r.companyId\r\n" + //
                    "        ,r.address\r\n" + //
                    "        ,r.type\r\n" + //
                    "        ,r.deadline\r\n" + //
                    "ORDER BY r.createdAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

            List<ApplyJobCompanyDTO> applyJobs = jdbcTemplate.query(query,
                    new BeanPropertyRowMapper<>(ApplyJobCompanyDTO.class), companyId, (page - 1) * size, size);

            int totalItem = jdbcTemplate.queryForObject(
                    "SELECT count(1) FROM recruitment r WHERE r.companyId = ?",
                    Integer.class, companyId);
            pagingModel.setData(applyJobs);
            pagingModel.setTotalItem(totalItem);
            pagingModel.setTotalPage((int) Math.ceil((double) totalItem / size));
            pagingModel.setCurrentPage(page);
            return pagingModel;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ArrayList<ApplyJobCompanyDetailDTO> getDetailApplyByCompany(int recruimentId) {
        try {
            String query = "SELECT\r\n" + //
                    "  aj.*\r\n" + //
                    " ,a.fullName AS accountName\r\n" + //
                    " ,a.email AS accountEmail\r\n" + //
                    " ,a.image AS accountImage\r\n" + //
                    " ,a.phoneNumber AS accountPhone\r\n" + //
                    " ,c.name AS cvName\r\n" + //
                    " ,c.filePath AS cvFilePath\r\n" + //
                    "FROM apply_job aj\r\n" + //
                    "JOIN recruitment r\r\n" + //
                    "  ON aj.recruitmentId = r.id\r\n" + //
                    "LEFT JOIN account a\r\n" + //
                    "  ON aj.accountId = a.id\r\n" + //
                    "LEFT JOIN cv c\r\n" + //
                    "  ON a.id = c.accountId\r\n" + //
                    "WHERE aj.recruitmentId = ?\r\n" + //
                    "order BY aj.createdAt ASC";

            return (ArrayList<ApplyJobCompanyDetailDTO>) jdbcTemplate.query(query,
                    new BeanPropertyRowMapper<>(ApplyJobCompanyDetailDTO.class), recruimentId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseMessage updateCvStatus(int applyJobId, int companyId, int status) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            boolean isExist = jdbcTemplate.queryForObject(
                    "SELECT COUNT(1) FROM apply_job aj JOIN recruitment r ON aj.recruitmentId = r.id WHERE aj.id = ? AND r.companyId = ?",
                    Integer.class, applyJobId, companyId) > 0;
            if (!isExist) {
                responseMessage.SetError("Không tìm thấy thông tin ứng tuyển.");
                return responseMessage;
            }

            // B2: update trạng thái
            int rowAffected = jdbcTemplate.update("UPDATE apply_job SET status = ?, updatedAt = GETDATE() WHERE id = ?",
                    status, applyJobId);

            String message = "";
            if (status == ApplyJobStatus.ACCEPTED.toInt()) {
                message = "Duyệt CV thành công.";
            } else if (status == ApplyJobStatus.REJECTED.toInt()) {
                message = "Từ chối CV thành công.";
            }

            if (rowAffected > 0) {
                responseMessage.SetSuccess(message);
            } else {
                responseMessage.SetError("Duyệt CV thất bại.");
            }
        } catch (Exception e) {
            responseMessage.SetError("Duyệt CV thất bại.");
        }
        return responseMessage;
    }
}
