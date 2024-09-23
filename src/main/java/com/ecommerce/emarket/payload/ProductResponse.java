package com.ecommerce.emarket.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;
    private List<ProductDTO> content;

    public static ProductResponse createProductList(List<ProductDTO> products) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(products);

        return productResponse;
    }
}
