package com.example.bussinessshope.product.controller;

import com.example.bussinessshope.product.dto.*;
import com.example.bussinessshope.product.service.ProductService;
import com.example.bussinessshope.security.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/api/product")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductService productService;
    private final JwtService jwtService;

    @Autowired
    public ProductController(ProductService productService, JwtService jwtService) {
        this.productService = productService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Add product", description = "Add a new product to the authenticated user's business")
    @ApiResponse(responseCode = "200", description = "Product added successfully")
    @PostMapping("/add")
    public ResponseEntity<ProductResponseDto> addProduct(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ProductRequestDto productRequestDto) {
        Long userId = jwtService.extractUserIdFromToken(token);
        ProductResponseDto product = productService.addProduct(userId, productRequestDto);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Update product", description = "Update an existing product belonging to the user")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdateDto productUpdateDto) {
        Long userId = jwtService.extractUserIdFromToken(token);
        ProductResponseDto updatedProduct = productService.updateProduct(userId, id, productUpdateDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Delete product", description = "Soft delete a product belonging to the user")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        Long userId = jwtService.extractUserIdFromToken(token);
        productService.softDeleteProduct(userId, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update product price", description = "Update the price of a specific product")
    @ApiResponse(responseCode = "200", description = "Product price updated successfully")
    @PutMapping("/update/{id}/price")
    public ResponseEntity<ProductResponseDto> updateProductPrice(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdatePriceDto priceDto) {
        Long userId = jwtService.extractUserIdFromToken(token);
        ProductResponseDto updatedProduct = productService.updatePrice(userId, id, priceDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Update product quantity", description = "Update the quantity of a specific product")
    @ApiResponse(responseCode = "200", description = "Product quantity updated successfully")
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
