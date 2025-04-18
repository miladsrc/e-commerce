package com.example.bussinessshope.product.controller;

import com.example.bussinessshope.product.dto.*;
import com.example.bussinessshope.product.service.ProductService;
import com.example.bussinessshope.security.service.JwtService;
import jakarta.servlet.http.HttpServlet;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final JwtService jwtService;

    @Autowired
    public ProductController(ProductService productService, JwtService jwtService) {
        this.productService = productService;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponseDto> addProduct(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ProductRequestDto productRequestDto) {
        Long userId = jwtService.extractUserIdFromToken(token);
        ProductResponseDto product = productService.addProduct(userId, productRequestDto);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdateDto productUpdateDto) {
        Long userId = jwtService.extractUserIdFromToken(token);
        ProductResponseDto updatedProduct = productService.updateProduct(userId, id, productUpdateDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        Long userId = jwtService.extractUserIdFromToken(token);
        productService.softDeleteProduct(userId, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}/price")
    public ResponseEntity<ProductResponseDto> updateProductPrice(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdatePriceDto priceDto) {
        Long userId = jwtService.extractUserIdFromToken(token);
        ProductResponseDto updatedProduct = productService.updatePrice(userId, id, priceDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/update/{id}/quantity")
    public ResponseEntity<ProductResponseDto> updateProductQuantity(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdateQuantityDto quantityDto) {
        Long userId = jwtService.extractUserIdFromToken(token);
        ProductResponseDto updatedProduct = productService.updateQuantity(userId, id, quantityDto);
        return ResponseEntity.ok(updatedProduct);
    }

}