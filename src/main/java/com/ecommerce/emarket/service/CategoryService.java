package com.ecommerce.emarket.service;

import com.ecommerce.emarket.payload.CategoryDTO;
import com.ecommerce.emarket.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    String deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
}
