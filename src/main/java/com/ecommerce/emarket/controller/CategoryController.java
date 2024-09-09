package com.ecommerce.emarket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.emarket.model.Category;
import com.ecommerce.emarket.service.CategoryService;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public List<Category> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories;
    }

    @PostMapping("/api/admin/categories")
    public String createCategory(@RequestBody Category category) {

        categoryService.createCategory(category);
        return "Category is created";

    }
}
