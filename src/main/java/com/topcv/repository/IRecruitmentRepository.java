package com.topcv.repository;

import com.topcv.model.*;

public interface IRecruitmentRepository {
    Recruitment detail(int id);

    int add(Recruitment recruitment);

    int delete(int id, int companyId);

    int update(Recruitment recruitment);

    PagingModel<Recruitment> list(int companyId, int page, int size);

    Company getCompany(int id);

    Category getCategory(int id);

    int applyCv(ApplyJob applyJob);

    ResponseMessage cancelApply(int id, int accountId);

    ResponseMessage deleteSaveJob(int id, int accountId);
}
