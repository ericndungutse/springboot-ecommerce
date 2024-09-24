package com.ecommerce.emarket.service;

import com.ecommerce.emarket.payload.ProductDTO;
import com.ecommerce.emarket.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse getProductsByKeyword(String keyword);
}
