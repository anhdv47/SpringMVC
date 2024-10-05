package com.topcv.repository;

import com.topcv.model.Company;
import com.topcv.model.HomeCount;
import com.topcv.model.PagingModel;
import com.topcv.model.Recruitment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HomeRepository implements IHomeRepository {
    private final JdbcTemplate jdbcTemplate;

    public HomeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Lấy ra số lượng ứng viên, công ty, tin tuyển dụng
     *
     * @return HomeCount
     */
    @Override
    public HomeCount getHomeCount() {
        try {
            String sql = "SELECT\n" + "  (SELECT\n" + "      COUNT(1)\n" + "    FROM account a\n"
                    + "    WHERE a.roleId = 1)\n" + "  AS numberCandidate\n" + " ,(SELECT\n" + "      COUNT(1)\n"
                    + "    FROM company)\n" + "  AS numberCompany\n" + " ,(SELECT\n" + "      COUNT(1)\n"
                    + "    FROM recruitment)\n" + "  AS numberRecruitment";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(HomeCount.class)).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Nullable
    public PagingModel<Recruitment> getAllRecruitment(int page, int size, String title, String type, String address) {
        try {
            PagingModel<Recruitment> pagingModel = new PagingModel<>();
            String sql = "SELECT\n" +
                    "  r.*, c.name AS companyName, c.logo AS companyLogo, c1.name AS categoryName,\n" +
                    "  (SELECT COUNT(1) FROM apply_job aj WHERE aj.recruitmentId = r.id) AS numOfApply\n" +
                    "FROM recruitment r\n" +
                    "JOIN company c ON r.companyId = c.id\n" +
                    "JOIN category c1 ON r.categoryId = c1.id\n" +
                    "WHERE r.deadline >= GETDATE()";

            // List chứa các tham số để truyền vào query
            List<Object> params = new ArrayList<>();

            // Thêm các điều kiện lọc nếu có
            if (title != null && !title.isEmpty()) {
                sql += " AND r.title LIKE ?";
                params.add("%" + title + "%"); // Sử dụng LIKE để tìm kiếm tiêu đề
            }
            if (type != null && !type.isEmpty()) {
                sql += " AND r.type = ?";
                params.add(type); // Lọc theo loại công việc
            }
            if (address != null && !address.isEmpty()) {
                sql += " AND r.address LIKE ?";
                params.add("%" + address + "%"); // Sử dụng LIKE để tìm kiếm địa chỉ
            }

            // Thêm phần sắp xếp và phân trang
            sql += " ORDER BY numOfApply DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            params.add((page - 1) * size); // Offset cho phân trang
            params.add(size); // Kích thước trang

            // Thực hiện query
            List<Recruitment> recruitments = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Recruitment.class), params.toArray());

            // Tính tổng số item
            String countSql = "SELECT COUNT(1) FROM recruitment r WHERE r.deadline >= GETDATE()";

            // Thêm các điều kiện lọc vào countSql nếu có
            if (title != null && !title.isEmpty()) {
                countSql += " AND r.title LIKE ?";
            }
            if (type != null && !type.isEmpty()) {
                countSql += " AND r.type = ?";
            }
            if (address != null && !address.isEmpty()) {
                countSql += " AND r.address LIKE ?";
            }

            params.remove(params.size() - 1);
            params.remove(params.size() - 1);

            // Query tổng số item
            int totalItem = jdbcTemplate.queryForObject(countSql, Integer.class, params.toArray());

            // Thiết lập kết quả trả về
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
    public PagingModel<Company> getAllCompany(int page, int size) {
        try {
            PagingModel<Company> pagingModel = new PagingModel<>();
            String sql = "SELECT\n" +
                    "  c.*\n" +
                    " ,r.totalApply\n" +
                    " ,r1.applyPosition\n" +
                    "FROM company c\n" +
                    "LEFT JOIN (SELECT\n" +
                    "    r.companyId AS companyId\n" +
                    "   ,COUNT(aj.id) totalApply\n" +
                    "  FROM recruitment r\n" +
                    "  LEFT JOIN apply_job aj\n" +
                    "    ON r.id = aj.recruitmentId\n" +
                    "  GROUP BY r.companyId) r\n" +
                    "  ON r.companyId = c.id\n" +
                    "LEFT JOIN (SELECT\n" +
                    "    r.companyId\n" +
                    "   ,COUNT(DISTINCT c.id) AS applyPosition\n" +
                    "  FROM recruitment r\n" +
                    "  LEFT JOIN category c\n" +
                    "    ON r.categoryId = c.id\n" +
                    "  GROUP BY r.companyId) r1\n" +
                    "  ON r1.companyId = c.id\n" +
                    "ORDER BY r.totalApply DESC\n";
            String sqlCount = " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
            List<Company> companies = jdbcTemplate.query(sql + sqlCount, new BeanPropertyRowMapper<>(Company.class),
                    (page - 1) * size, size);
            int totalItem = jdbcTemplate.queryForObject("select count(1) FROM company c", Integer.class);
            pagingModel.setData(companies);
            pagingModel.setTotalItem(totalItem);
            pagingModel.setTotalPage((int) Math.ceil((double) totalItem / size));
            pagingModel.setCurrentPage(page);
            return pagingModel;
        } catch (Exception e) {
            return null;
        }
    }
}
