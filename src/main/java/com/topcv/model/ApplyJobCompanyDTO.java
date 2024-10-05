package com.topcv.model;

public class ApplyJobCompanyDTO extends Recruitment {
    private int totalApplied;

    public ApplyJobCompanyDTO() {

    }

    public int getTotalApplied() {
        return totalApplied;
    }

    public void setTotalApplied(int totalApplied) {
        this.totalApplied = totalApplied;
    }
}
