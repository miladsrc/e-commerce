package com.example.bussinessshope.shared.exception.specified;

import java.util.Map;

public class InsufficientProductStockException extends RuntimeException {
    private final Map<Long, Integer> insufficientProducts;

    public InsufficientProductStockException(Map<Long, Integer> insufficientProducts) {
        super("Some products do not have enough stock!");
        this.insufficientProducts = insufficientProducts;
    }

    public Map<Long, Integer> getInsufficientProducts() {
        return insufficientProducts;
    }
}
