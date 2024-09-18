package com.ecommerce.emarket.service;

import com.ecommerce.emarket.model.Category;
import com.ecommerce.emarket.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category category);
}
