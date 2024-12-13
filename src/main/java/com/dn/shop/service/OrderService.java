package com.dn.shop.service;

import com.dn.shop.exception.ResourceNotFoundException;
import com.dn.shop.model.entity.Order;
import com.dn.shop.model.entity.OrderStatus;
import com.dn.shop.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public ResponseEntity<Order> createOrder(@Valid Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (order.getUser() == null) {
            throw new IllegalArgumentException("Order must have an associated user");
        }

        log.info("Creating new order for user: {}", order.getUser().getId());
        order.calculateTotalPrice();
        order.setStatus(OrderStatus.CREATED);
        Order savedOrder = orderRepository.save(order);
        log.debug("Order created successfully with ID: {}", savedOrder.getId());
        return ResponseEntity.ok(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        log.info("Fetching orders for user: {}", userId);
        return orderRepository.findByUser_Id(userId);
    }

    @Transactional
    public ResponseEntity<Order> updateOrderStatus(Long orderId, OrderStatus status) {
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        if (status == null) {
            throw new IllegalArgumentException("Order status cannot be null");
        }

        log.info("Updating order {} status to: {}", orderId, status);
        
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Order not found with id: %d", orderId)));
        
        validateStatusTransition(order.getStatus(), status);
        
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        log.debug("Order {} status updated successfully to {}", orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change status of cancelled order");
        }
    }
} 