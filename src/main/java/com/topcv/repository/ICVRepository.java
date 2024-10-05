package com.topcv.repository;

import com.topcv.model.Cv;

import java.util.List;

public interface ICVRepository {
    Cv getCv(int cvId, int accountId, boolean isEmployer);

    int insert(Cv cv);

    int update(Cv cv);

    int delete(int id);

    List<Cv> getAllCv(int accountId);

    boolean isCvApplied(int cvId, int accountId, int recruitmentId);
}
