package com.ecommerce.emarket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.emarket.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT cartItem FROM CartItem cartItem WHERE cartItem.product.productId = ?1 AND cartItem.cart.cartId = ?2")
    CartItem findCartItemByProductIdAndCartId(Long productId, Long cartId);

    @Query("SELECT cartItem FROM CartItem cartItem WHERE cartItem.cart.cartId = ?1")
    List<CartItem> findCartItemsByCartId(Long cartId);

}
