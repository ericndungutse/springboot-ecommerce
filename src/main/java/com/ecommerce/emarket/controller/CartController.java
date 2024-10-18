package com.ecommerce.emarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.emarket.payload.CartDTO;
import com.ecommerce.emarket.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/users/carts")
    public ResponseEntity<CartDTO> getUserCart() {
        CartDTO cartDTO = cartService.getMyCart();
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PutMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> updateProductQuantity(@PathVariable Long productId,
            @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.updateCartProductQuantity(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

}
