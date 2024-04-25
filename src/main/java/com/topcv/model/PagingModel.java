package com.topcv.model;

import java.util.List;

public class PagingModel<T> {
    private List<T> data;
    private int totalPage;
    private int currentPage;
    private int totalItem;

    public PagingModel(List<T> data, int totalPage, int currentPage, int totalItem) {
        this.data = data;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.totalItem = totalItem;
    }

    public PagingModel() {
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }
}
