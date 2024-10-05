package com.topcv.model;

public class SaveJobDTO extends SaveJob {
    private int recruitmentId;
    private String recruitmentType;
    private String recruitmentTitle;
    private String recruitmentAddress;
    private String companyName;
    private String companyLogo;

    public SaveJobDTO() {
    }


    @Override
    public int getRecruitmentId() {
        return recruitmentId;
    }

    @Override
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
}
