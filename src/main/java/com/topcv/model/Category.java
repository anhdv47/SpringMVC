package com.topcv.model;

public class Category extends BaseEntity {
    private String name;

    public Category() {
    }

    public Category(int id, String created_at, String updated_at, String name) {
        super(id, created_at, updated_at);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
