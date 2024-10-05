package com.topcv.repository;


public interface IGenericRepository<T> {
    T getById(int id);

    int insert(T t);

    int update(T t);

    int delete(int id);
}
