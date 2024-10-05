package com.topcv.repository;

import com.topcv.model.ApplyJobCompanyDTO;
import com.topcv.model.ApplyJobCompanyDetailDTO;
import com.topcv.model.PagingModel;
import com.topcv.model.ResponseMessage;

import java.util.ArrayList;

public interface IEmployerRepository {
    PagingModel<ApplyJobCompanyDTO> getListApplyByCompany(int id, int page, int size);

    ArrayList<ApplyJobCompanyDetailDTO> getDetailApplyByCompany(int id);

    ResponseMessage updateCvStatus(int applyJobId, int companyId, int status);
}
