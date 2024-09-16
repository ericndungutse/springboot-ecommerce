package com.ecommerce.emarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.emarket.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);

}
