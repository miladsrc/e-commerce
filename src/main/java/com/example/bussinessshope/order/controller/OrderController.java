package com.example.bussinessshope.order.controller;

import com.example.bussinessshope.order.dto.OrderRequestDto;
import com.example.bussinessshope.order.dto.OrderResponseDto;
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
    public ResponseEntity<OrderResponseDto> getCurrentOrder(@RequestHeader("Authorization") String token) {
        Long userId = jwtService.extractUserId(token.substring(7));
        OrderResponseDto order = orderService.getCurrentOrder(userId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/update")
    public ResponseEntity<OrderResponseDto> updateOrder(@RequestHeader("Authorization") String token,
                                                        @RequestBody @Valid OrderRequestDto orderRequestDto) {
        Long userId = jwtService.extractUserId(token.substring(7));
        OrderResponseDto updatedOrder = orderService.updateOrder(userId, orderRequestDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/finalize")
    public ResponseEntity<OrderResponseDto> finalizeOrder(@RequestHeader("Authorization") String token) {
        Long userId = jwtService.extractUserId(token.substring(7));
        OrderResponseDto finalizedOrder = orderService.finalizeOrder(userId);
        return ResponseEntity.ok(finalizedOrder);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(@RequestHeader("Authorization") String token) {
        Long userId = jwtService.extractUserId(token.substring(7));
        List<OrderResponseDto> orderHistory = orderService.getOrderHistory(userId);
        return ResponseEntity.ok(orderHistory);
    }
}