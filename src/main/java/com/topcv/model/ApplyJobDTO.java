package com.topcv.model;

public class ApplyJobDTO extends ApplyJob {
    private int recruitmentId;
    private String recruitmentType;
    private String recruitmentTitle;
    private String recruitmentAddress;
    private String companyName;
    private String companyLogo;
    private String cvName;
    private String cvPath;

    public int getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(int recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public String getRecruitmentType() {
        return recruitmentType;
    }

    public void setRecruitmentType(String recruitmentType) {
        this.recruitmentType = recruitmentType;
    }

    public String getRecruitmentTitle() {
        return recruitmentTitle;
    }

    public void setRecruitmentTitle(String recruitmentTitle) {
        this.recruitmentTitle = recruitmentTitle;
    }

    public String getRecruitmentAddress() {
        return recruitmentAddress;
    }

    public void setRecruitmentAddress(String recruitmentAddress) {
        this.recruitmentAddress = recruitmentAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCvName() {
        return cvName;
    }

    public void setCvName(String cvName) {
        this.cvName = cvName;
    }

    public String getCvPath() {
        return cvPath;
    }

    public void setCvPath(String cvPath) {
        this.cvPath = cvPath;
    }
}
