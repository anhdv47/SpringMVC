package com.topcv.repository;

import com.topcv.model.Category;
import com.topcv.model.Company;
import com.topcv.model.Recruitment;

import java.util.List;

public interface IJobRepository {
    Recruitment detail(int id);

    int add(Recruitment recruitment);

    int delete(int id);

    int update(Recruitment recruitment);

    List<Recruitment> list();

    Company getCompany(int id);

    Category getCategory(int id);
}
