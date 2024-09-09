package com.ecommerce.emarket.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.emarket.model.Category;

@RestController
public class CategoryController {
    private List<Category> catefories = new ArrayList<>();

    @GetMapping("/api/public/categories")
    public List<Category> getAllCategories() {
        return catefories;
    }

    @PostMapping("/api/admin/categories")
    public String createCategory(@RequestBody Category category) {
        catefories.add(category);
        return "Category is created";
    }
}
