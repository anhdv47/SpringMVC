package com.topcv.repository;

import com.topcv.model.*;

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
