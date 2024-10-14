package com.ecommerce.emarket.payload;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartId;
    private Double totalPrice = 0.0;
    private List<CartItemDto> cartItems = new ArrayList<>();
    // private List<ProductDTO> products = new ArrayList<>();

}
