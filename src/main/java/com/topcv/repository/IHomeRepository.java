package com.topcv.repository;

import com.topcv.model.Company;
import com.topcv.model.HomeCount;
import com.topcv.model.PagingModel;
import com.topcv.model.Recruitment;

public interface IHomeRepository {
    HomeCount getHomeCount();

    PagingModel<Recruitment> getAllRecruitment(int page, int size, String title, String type, String address);

    PagingModel<Company> getAllCompany(int page, int size);
}
