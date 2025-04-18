package com.example.bussinessshope.shared.exception.specified;

public class ProductNotFoundException extends RuntimeException {
    private final Long productId;

    public ProductNotFoundException(String productId) {
        super("Product with ID " + productId + " not found!");
        this.productId = Long.valueOf(productId);
    }

    public Long getProductId() {
        return productId;
    }
}
