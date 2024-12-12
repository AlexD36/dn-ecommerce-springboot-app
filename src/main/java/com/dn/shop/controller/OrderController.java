package com.dn.shop.controller;

import com.dn.shop.model.entity.Order;
import com.dn.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Endpoint to create a new order
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    // Endpoint to fetch all orders for a specific user
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    // Endpoint to update the order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }
} 