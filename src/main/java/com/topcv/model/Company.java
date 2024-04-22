package com.topcv.model;

public class Company extends BaseEntity {
    private String address;
    private String description;
    private String email;
    private String logo;
    private String name;
    private String phoneNumber;
    private int status;

    public Company(String address, String description, String email, String logo, String name, String phoneNumber, int status) {
        this.address = address;
        this.description = description;
        this.email = email;
        this.logo = logo;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Company(int id, String created_at, String updated_at, String address, String description, String email, String logo, String name, String phoneNumber, int status) {
        super(id, created_at, updated_at);
        this.address = address;
        this.description = description;
        this.email = email;
        this.logo = logo;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
