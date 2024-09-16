package com.ecommerce.emarket.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s with %s: %s not found", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s with %s: %s not found", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

    // @Getter
    // @Setter
    // String resourceName;
    // @Getter
    // @Setter
    // String fieldName;
    // @Getter
    // @Setter
    // Object fieldValue;

}
