package com.ecommerce.emarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
