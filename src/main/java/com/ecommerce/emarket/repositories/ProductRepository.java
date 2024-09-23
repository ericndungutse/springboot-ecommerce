package com.ecommerce.emarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.emarket.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
