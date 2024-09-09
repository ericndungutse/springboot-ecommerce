package com.ecommerce.emarket.service;

import java.util.List;

import com.ecommerce.emarket.model.Category;

public interface CategoryService {
    List<Category> getAllCategories();

    void createCategory(Category category);
}
