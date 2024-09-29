package com.ecommerce.emarket.service;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.emarket.payload.ProductDTO;
import com.ecommerce.emarket.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image);
}
