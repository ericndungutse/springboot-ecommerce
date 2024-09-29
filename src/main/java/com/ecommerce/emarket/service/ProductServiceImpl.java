package com.ecommerce.emarket.service;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.emarket.exceptions.APIException;
import com.ecommerce.emarket.exceptions.ResourceNotFoundException;
import com.ecommerce.emarket.model.Category;
import com.ecommerce.emarket.model.Product;
import com.ecommerce.emarket.payload.ProductDTO;
import com.ecommerce.emarket.payload.ProductResponse;
import com.ecommerce.emarket.repositories.CategoryRepository;
import com.ecommerce.emarket.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    FileService fileService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${project.images}")
    private String path;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        // Get Category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        // CHeck if product already exists in the category
        if (productRepository.existsByProductNameAndCategory(productDTO.getProductName(), category)) {
            throw new APIException("Product already exists in the category");
        }

        // Map DTO to Entity
        Product product = modelMapper.map(productDTO, Product.class);
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

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Product> products = productRepository.findByCategory(category);

        // Map them to productDTO
        List<ProductDTO> productDTOs = products.stream().map((product) -> modelMapper.map(product, ProductDTO.class))
                .toList();

        // Return the ProductResponse object
        return ProductResponse.createProductList(productDTOs);
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        // % coz we are using pattern matching with LIKE
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%");

        // Map them to productDTO
        List<ProductDTO> productDTOs = products.stream().map((product) -> modelMapper.map(product, ProductDTO.class))
                .toList();

        // Return the ProductResponse object
        return ProductResponse.createProductList(productDTOs);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        // Find the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setDiscount(productDTO.getDiscount());
        product.setPrice(productDTO.getPrice());
        productDTO.setQuantity(productDTO.getQuantity());

        double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
        product.setSpecialPrice(specialPrice);

        productRepository.save(product);

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.delete(product);

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) {
        // Get Product from DB
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        // Upload image to server(Our server) in /images folder and Get file name of
        // uploaded image

        String filename;
        try {
            filename = fileService.uploaImage(path, image);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        // Update the image field in the product
        product.setImage(filename);

        // Save the product
        productRepository.save(product);

        // Return the productDTO
        return modelMapper.map(product, ProductDTO.class);
    }

}
