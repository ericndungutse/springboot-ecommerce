package com.ecommerce.emarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.emarket.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}