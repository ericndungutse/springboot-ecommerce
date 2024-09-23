package com.ecommerce.emarket.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.ecommerce.emarket.exceptions.ResourceNotFoundException;
import com.ecommerce.emarket.model.Product;
import com.ecommerce.emarket.payload.ProductDTO;
import com.ecommerce.emarket.payload.ProductResponse;
import com.ecommerce.emarket.repositories.CategoryRepository;
import com.ecommerce.emarket.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        // Map DTO to Entity
        Product product = modelMapper.map(productDTO, Product.class);

        // Get Category
        com.ecommerce.emarket.model.Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        // Set Category
        product.setCategory(category);
        double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
        product.setSpecialPrice(specialPrice);
        product.setImage("default.png");

        // Create Product
        Product newProduct = productRepository.save(product);

        return modelMapper.map(newProduct, ProductDTO.class);

    }

    @Override
    public ProductResponse getAllProducts() {
        // Get List of all products
        List<Product> products = productRepository.findAll();

        // Map them to productDTO
        List<ProductDTO> productDTOs = products.stream().map((product) -> modelMapper.map(product, ProductDTO.class))
                .toList();

        // Return the ProductResponse object
        return ProductResponse.createProductList(productDTOs);
    }

}
