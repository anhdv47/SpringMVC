package com.topcv.model;

public class Role extends BaseEntity {
    private String roleName;

    public Role() {
    }

    public Role(int id, String created_at, String updated_at, String roleName) {
        super(id, created_at, updated_at);
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
