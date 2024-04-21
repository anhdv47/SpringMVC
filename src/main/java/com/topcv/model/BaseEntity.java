package com.topcv.model;

public class BaseEntity {
    private int id;
    private String created_at;
    private String updated_at;

    public BaseEntity() {
    }

    public BaseEntity(int id, String created_at, String updated_at) {
        this.id = id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}
