package com.example.bussinessshope.order.service;

import com.example.bussinessshope.order.dto.OrderRequestDto;
import com.example.bussinessshope.order.dto.OrderResponseDto;
import com.example.bussinessshope.order.entity.OrderEntity;
import com.example.bussinessshope.order.repository.OrderRepository;
import com.example.bussinessshope.product.entity.ProductEntity;
import com.example.bussinessshope.product.repository.ProductRepository;
import com.example.bussinessshope.shared.entity.Situation;
import com.example.bussinessshope.shared.entity.Transaction;
import com.example.bussinessshope.shared.exception.OrderNotFoundException;
import com.example.bussinessshope.shared.exception.UserNotFoundException;
import com.example.bussinessshope.user.entity.UserEntity;
import com.example.bussinessshope.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

        OrderEntity order = orderRepository.findByUserIdAndSituation(userId, Situation.Pending)
                .orElseGet(() -> {
                    OrderEntity newOrder = new OrderEntity();
                    newOrder.setUser(user);
                    newOrder.setCheckFactorTime(LocalDateTime.now());
                    newOrder.setTransaction(new Transaction(null, 0.0));
                    newOrder.setSituation(Situation.Pending);
                    return orderRepository.save(newOrder);
                });
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long userId, OrderRequestDto orderRequestDto) {
        OrderEntity order = orderRepository.findByUserIdAndSituation(userId, Situation.Pending)
                .orElseThrow(() -> new OrderNotFoundException("Order with pending situation not found!"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with id " + userId + " does not exist!"));

        List<ProductEntity> productList = productRepository.findAllById(orderRequestDto.getProductIds());

        order.setUser(user);
        order.setProductList(productList);
        order.setCheckFactorTime(orderRequestDto.getCheckFactorTime());
        order.setTransaction(new Transaction(null, orderRequestDto.getCostFactor()));
        order.setSituation(orderRequestDto.getSituation());

        orderRepository.save(order);

        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Transactional
    public OrderResponseDto finalizeOrder(Long userId) {
        OrderEntity order = orderRepository.findByUserIdAndSituation(userId, Situation.Pending)
                .orElseThrow(() -> new OrderNotFoundException("No pending order found"));
        order.getTransaction().setTransactionTime(LocalDateTime.now());
        order.setSituation(Situation.Done);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponseDto.class);
    }

    public List<OrderResponseDto> getOrderHistory(Long userId) {
        return orderRepository.findByUserIdAndSituationNot(userId, Situation.Pending)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());
    }

}