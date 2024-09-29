package com.ecommerce.emarket.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotBlank
    @Size(min = 3, message = "Product name must be at least 3 characters long")
    private String productName;
    @NotBlank
    @Size(min = 3, message = "Product name must be at least 3 characters long")
    private String description;
    @NotNull(message = "Category is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private double price;
    private String image;
    private double discount;
    private double specialPrice;
}
