package com.ecommerce.emarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.emarket.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
