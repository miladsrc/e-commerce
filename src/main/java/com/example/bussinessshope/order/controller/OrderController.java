package com.example.bussinessshope.order.controller;

import com.example.bussinessshope.order.dto.OrderResponseDto;
import com.example.bussinessshope.order.dto.OrderUpdateDto;
import com.example.bussinessshope.order.service.OrderService;
import com.example.bussinessshope.security.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;
    private final JwtService jwtService;

    @Autowired
    public OrderController(OrderService orderService, JwtService jwtService) {
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Get current order", description = "Returns the current active order for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Current order retrieved successfully")
    @GetMapping("/current")
    public ResponseEntity<OrderResponseDto> getCurrentOrder(
            @RequestHeader("Authorization") String key) {
        Long userId = jwtService.extractUserIdFromToken(key);
        OrderResponseDto order = orderService.getCurrentOrder(userId);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Update order", description = "Update current order details such as quantity or items")
    @ApiResponse(responseCode = "200", description = "Order updated successfully")
    @PutMapping("/update")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @RequestHeader("Authorization") String key,
            @RequestBody @Valid OrderUpdateDto orderUpdateDto) {
        Long userId = jwtService.extractUserIdFromToken(key);
        OrderResponseDto updatedOrder = orderService.updateOrder(userId, orderUpdateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "Finalize order", description = "Finalize the current order and proceed to transaction")
    @ApiResponse(responseCode = "200", description = "Order finalized successfully")
    @PostMapping("/finalize")
    public ResponseEntity<OrderResponseDto> finalizeOrder(
            @RequestHeader("Authorization") String key) {
        Long userId = jwtService.extractUserIdFromToken(key);
        OrderResponseDto finalizedOrder = orderService.finalizeOrder(userId);
        return ResponseEntity.ok(finalizedOrder);
    }

    @Operation(summary = "Get order history", description = "Retrieve all finalized orders of the authenticated user")
    @ApiResponse(responseCode = "200", description = "Order history fetched successfully")
    @GetMapping("/history")
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(
            @RequestHeader("Authorization") String key) {
        Long userId = jwtService.extractUserIdFromToken(key);
        List<OrderResponseDto> orderHistory = orderService.getOrderHistory(userId);
        return ResponseEntity.ok(orderHistory);
    }

    @Operation(summary = "Remove a product from user's pending orders", description = "Deletes the specified product from all pending orders of the authenticated user")
    @ApiResponse(responseCode = "200", description = "Product removed from pending orders successfully")
    @DeleteMapping("/pending/products/{productId}")
    public ResponseEntity<Void> removeProductFromUserPendingOrders(
            @RequestHeader("Authorization") String key,
            @PathVariable Long productId) {
        Long userId = jwtService.extractUserIdFromToken(key);
        orderService.removeProductFromUserPendingOrders(userId, productId);
        return ResponseEntity.ok().build();
    }
}
