package com.ecommerce.emarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.emarket.model.Category;
import com.ecommerce.emarket.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository repository;

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        repository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category with id " + categoryId + " is not found"));

        repository.delete(category);
        return "Category with id " + categoryId + " is deleted";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Category existingCategory = repository.findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category with id " + categoryId + " is not found"));

        existingCategory.setCategoryName(category.getCategoryName());
        repository.save(existingCategory);
        return existingCategory;
    }

}
