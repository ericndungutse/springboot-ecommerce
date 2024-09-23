package com.ecommerce.emarket.service;

import com.ecommerce.emarket.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);
}
