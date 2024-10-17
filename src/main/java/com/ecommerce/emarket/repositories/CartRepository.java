package com.ecommerce.emarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.emarket.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Query to find cart by email. ? is the parameter placeholder. One means first
    // parameter
    @Query("SELECT cart FROM Cart cart WHERE cart.user.email = ?1")
    Cart findCartByEmail(String email);
}