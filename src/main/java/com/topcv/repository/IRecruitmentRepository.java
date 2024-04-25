package com.topcv.repository;

import com.topcv.model.Category;
import com.topcv.model.Company;
import com.topcv.model.Recruitment;

import java.util.List;

public interface IRecruitmentRepository {
    Recruitment detail(int id);

    int add(Recruitment recruitment);

    int delete(int id, int companyId);

    int update(Recruitment recruitment);

    List<Recruitment> list(int companyId);

    Company getCompany(int id);

    Category getCategory(int id);
}
