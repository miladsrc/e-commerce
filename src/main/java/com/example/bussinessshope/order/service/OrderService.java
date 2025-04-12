package com.example.bussinessshope.order.service;

import com.example.bussinessshope.order.dto.OrderResponseDto;
import com.example.bussinessshope.order.dto.OrderUpdateDto;
import com.example.bussinessshope.order.entity.OrderEntity;
import com.example.bussinessshope.order.repository.OrderRepository;
import com.example.bussinessshope.product.entity.ProductEntity;
import com.example.bussinessshope.product.repository.ProductRepository;
import com.example.bussinessshope.shared.entity.Situation;
import com.example.bussinessshope.shared.entity.Transaction;
import com.example.bussinessshope.shared.exception.InsufficientProductStockException;
import com.example.bussinessshope.shared.exception.OrderNotFoundException;
import com.example.bussinessshope.shared.exception.ProductNotFoundException;
import com.example.bussinessshope.user.entity.UserEntity;
import com.example.bussinessshope.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public OrderResponseDto getCurrentOrder(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        OrderEntity order = orderRepository.findByUserIdAndSituation(userId, Situation.PENDING)
                .orElseGet(() -> {
                    OrderEntity newOrder = new OrderEntity();
                    newOrder.setUser(user);
                    newOrder.setCheckFactorTime(LocalDateTime.now());
                    newOrder.setTransaction(new Transaction(null, 0.0));
                    newOrder.setSituation(Situation.PENDING);
                    return orderRepository.save(newOrder);
                });
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long userId, OrderUpdateDto orderUpdateDto) {
        OrderEntity order = orderRepository.findByUserIdAndSituation(userId, Situation.PENDING)
                .orElseThrow(() -> new OrderNotFoundException("Order with pending situation not found!"));

        List<ProductEntity> requestedProducts = orderUpdateDto.getProductList();

        // بررسی موجودی محصول‌ها
        Map<Long, Integer> insufficientProducts = new HashMap<>();
        for (ProductEntity requestedProduct : requestedProducts) {
            ProductEntity productInDb = productRepository.findById(requestedProduct.getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product with ID " + requestedProduct.getId() + " not found!"));

            if (productInDb.getQuantity() < requestedProduct.getQuantity()) {
                insufficientProducts.put(productInDb.getId(), requestedProduct.getQuantity());
            }
        }

        // اگر محصولی موجودی کافی نداشت، Exception بده
        if (!insufficientProducts.isEmpty()) {
            throw new InsufficientProductStockException(insufficientProducts);
        }

        // اگر مشکلی نبود، لیست محصولات را بروزرسانی کن
        List<ProductEntity> existingProducts = order.getProductList();
        existingProducts.removeIf(product -> !requestedProducts.contains(product));
        for (ProductEntity product : requestedProducts) {
            if (!existingProducts.contains(product)) {
                existingProducts.add(product);
            }
        }

        order.setProductList(existingProducts);
        orderRepository.save(order);

        return modelMapper.map(order, OrderResponseDto.class);
    }


    @Transactional
    public OrderResponseDto finalizeOrder(Long userId) {
        OrderEntity order = orderRepository.findByUserIdAndSituation(userId, Situation.PENDING)
                .orElseThrow(() -> new OrderNotFoundException("No pending order found"));
        order.getTransaction().setTransactionTime(LocalDateTime.now());
        order.setSituation(Situation.DONE);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponseDto.class);
    }

    public List<OrderResponseDto> getOrderHistory(Long userId) {
        return orderRepository.findByUserIdAndSituationNotOrderByCheckFactorTimeDesc(userId, Situation.PENDING)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());
    }

}