package com.topcv.repository;

import com.topcv.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ICategoryRepository {
    List<Category> GetAll();
}
