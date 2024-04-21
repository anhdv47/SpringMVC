package com.topcv.model;

public class Role extends BaseEntity {
    private String role_name;

    public Role() {
    }

    public Role(int id, String created_at, String updated_at, String role_name) {
        super(id, created_at, updated_at);
        this.role_name = role_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
