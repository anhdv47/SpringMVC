package com.topcv.model;

public class Account extends BaseEntity {
    private String address;
    private String description;
    private String email;
    private String full_name;
    private String image;
    private String password;
    private String phone_number;
    private Integer status;
    private Integer role_id;

    public Account() {
    }

    public Account(int id, String created_at, String updated_at, String address, String description, String email, String full_name, String image, String password, String phone_number, int status, int role_id) {
        super(id, created_at, updated_at);
        this.address = address;
        this.description = description;
        this.email = email;
        this.full_name = full_name;
        this.image = image;
        this.password = password;
        this.phone_number = phone_number;
        this.status = status;
        this.role_id = role_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
