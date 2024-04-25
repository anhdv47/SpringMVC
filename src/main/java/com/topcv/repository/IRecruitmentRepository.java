package com.topcv.repository;

import com.topcv.model.Category;
import com.topcv.model.Company;
import com.topcv.model.PagingModel;
import com.topcv.model.Recruitment;

import java.util.List;

public interface IRecruitmentRepository {
    Recruitment detail(int id);

    int add(Recruitment recruitment);

    int delete(int id, int companyId);

    int update(Recruitment recruitment);

    PagingModel<Recruitment> list(int companyId, int page, int size);

    Company getCompany(int id);

    Category getCategory(int id);
}
