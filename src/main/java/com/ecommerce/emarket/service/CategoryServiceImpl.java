package com.ecommerce.emarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.emarket.exceptions.APIException;
import com.ecommerce.emarket.exceptions.ResourceNotFoundException;
import com.ecommerce.emarket.model.Category;
import com.ecommerce.emarket.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository repository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = repository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }
        return repository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = repository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists");
        }
        repository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        repository.delete(category);
        return "Category with id " + categoryId + " is deleted";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Category existingCategory = repository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        existingCategory.setCategoryName(category.getCategoryName());
        repository.save(existingCategory);
        return existingCategory;
    }

}
