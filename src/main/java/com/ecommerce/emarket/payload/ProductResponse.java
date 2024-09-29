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

    public static ProductResponse createProductList(List<ProductDTO> products, int pageNumber, int pageSize,
            long totalElements, int totalPages, boolean lastPage) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(products);
        productResponse.setPageNumber(pageNumber + 1);
        productResponse.setPageSize(pageSize);
        productResponse.setTotalElements(totalElements);
        productResponse.setTotalPages(totalPages);
        productResponse.setLastPage(lastPage);

        return productResponse;
    }

    public ProductResponse(List<ProductDTO> content) {
        this.content = content;
    }

}
