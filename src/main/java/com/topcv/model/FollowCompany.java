package com.topcv.model;

public class FollowCompany extends BaseEntity {
    private int companyId;
    private int accountId;

    public int getCompany_id() {
        return companyId;
    }

    public void setCompany_id(int companyId) {
        this.companyId = companyId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
