package com.ecommerce.emarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "categories")
public class Category {
    @Id
    // GenerationType: Auto: Hibernate selects the generation strategy based on the
    // database-specific dialect.
    // GenerationType.IDENTITY: Hibernate relies on an auto-incremented database
    // column to generate the primary key. Not supported by all dbs.
    // GenerationType.SEQUENCE: Hibernate uses a database sequence to generate the
    // primary key. Used by Dbs that support sequences.
    // GenerationType.TABLE: Hibernate uses a database table to simulate a sequence.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;

    public Category() {
    }

    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
