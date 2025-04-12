package com.example.bussinessshope.order.controller;

import com.example.bussinessshope.order.dto.OrderRequestDto;
import com.example.bussinessshope.order.dto.OrderResponseDto;
import com.example.bussinessshope.order.dto.OrderUpdateDto;
import com.example.bussinessshope.order.service.OrderService;
import com.example.bussinessshope.security.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final JwtService jwtService;

    @Autowired
    public OrderController(OrderService orderService, JwtService jwtService) {
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    @GetMapping("/current")
    public ResponseEntity<OrderResponseDto> getCurrentOrder(
            @RequestHeader("Authorization") String key) {
        Long userId = jwtService.extractUserIdFromToken(key);
        OrderResponseDto order = orderService.getCurrentOrder(userId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/update")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @RequestHeader("Authorization") String key,
            @RequestBody @Valid OrderUpdateDto orderUpdateDto) {
        Long userId = jwtService.extractUserIdFromToken(key);
        OrderResponseDto updatedOrder = orderService.updateOrder(userId, orderUpdateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/finalize")
    public ResponseEntity<OrderResponseDto> finalizeOrder(
            @RequestHeader("Authorization") String key) {
        Long userId = jwtService.extractUserIdFromToken(key);
        OrderResponseDto finalizedOrder = orderService.finalizeOrder(userId);
        return ResponseEntity.ok(finalizedOrder);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(
            @RequestHeader("Authorization") String key) {
        Long userId = jwtService.extractUserIdFromToken(key);
        List<OrderResponseDto> orderHistory = orderService.getOrderHistory(userId);
        return ResponseEntity.ok(orderHistory);
    }
}