package com.ecommerce.emarket.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        Category category = categoryRepository.findById(categoryId)
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
        String path = "images/";
        String filename;
        try {
            filename = uploaImage(path, image);

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

    private String uploaImage(String path, MultipartFile file) throws IOException {
        // Get Current
        String originalFileName = file.getOriginalFilename();
        // return originalFileName;

        // Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        if (originalFileName == null) {
            throw new IOException("Original file name is null");
        }
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        // Why File.separator not just / or \ ? Because it is platform independent
        String filePath = path + File.separator + fileName;

        // Check if path exists or create it
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // // Return the file name
        return fileName;
    }

}
