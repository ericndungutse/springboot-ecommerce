package com.ecommerce.emarket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.emarket.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
    private List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {

        category.setCategoryId(nextId);
        nextId++;
        categories.add(category);

    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories
                .stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Category with id " + categoryId + " is not found"));

        if (category == null) {
            return "Category with id " + categoryId + " is not found";
        }
        categories.remove(category);
        return "Category with id " + categoryId + " is deleted";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Category existingCategory = categories
                .stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Category with id " + categoryId + " is not found"));

        existingCategory.setCategoryName(category.getCategoryName());
        return existingCategory;
    }

}
