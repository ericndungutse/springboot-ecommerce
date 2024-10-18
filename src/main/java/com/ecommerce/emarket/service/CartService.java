package com.ecommerce.emarket.service;

import com.ecommerce.emarket.payload.CartDTO;

public interface CartService {
    public CartDTO addProductToCart(Long productId, Integer quantity);

    public CartDTO getMyCart();

    public CartDTO updateCartProductQuantity(Long productId, Integer quantity);
}