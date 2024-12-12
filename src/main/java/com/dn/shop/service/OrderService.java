package com.dn.shop.service;

import com.dn.shop.model.entity.Order;
import com.dn.shop.model.entity.User;
import com.dn.shop.repository.OrderRepository;
import com.dn.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // Method to create a new order
    public ResponseEntity<String> createOrder(Order order) {
        // Calculate total price before saving
        order.calculateTotalPrice(); // Ensure total price is calculated
        orderRepository.save(order); // Save the order
        return ResponseEntity.ok("Order created successfully!");
    }

    // Method to fetch all orders for a specific user
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUser_Id(userId); // Fetch orders by user ID
    }

    // Method to update the order status
    public ResponseEntity<String> updateOrderStatus(Long orderId, String status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status); // Update the status
            orderRepository.save(order); // Save the updated order
            return ResponseEntity.ok("Order status updated successfully!");
        }
        return ResponseEntity.notFound().build(); // Return 404 if order not found
    }
} 