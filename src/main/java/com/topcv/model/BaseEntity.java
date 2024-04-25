package com.topcv.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class BaseEntity {
    private int id;
    private Date createdAt;
    private Date updatedAt;

    public BaseEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = setDateByString(createdAt);
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = setDateByString(updatedAt);
    }

    public Date setDateByString(String date) {
        try {
            if (date == null || date.isEmpty()) {
                return null;
            }
            DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
