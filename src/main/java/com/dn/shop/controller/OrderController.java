package com.dn.shop.controller;

import com.dn.shop.model.entity.Order;
import com.dn.shop.model.entity.OrderStatus;
import com.dn.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "Endpoints for managing orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order", 
               description = "Creates a new order with the provided details")
    @ApiResponse(responseCode = "200", description = "Order created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid order data")
    @PostMapping
    public ResponseEntity<Order> createOrder(
        @Parameter(description = "Order details") @RequestBody Order order
    ) {
        return orderService.createOrder(order);
    }

    @Operation(summary = "Get orders by user", 
               description = "Retrieves all orders for a specific user")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(
        @Parameter(description = "ID of the user") @PathVariable Long userId
    ) {
        return orderService.getOrdersByUser(userId);
    }

    @Operation(summary = "Update order status", 
               description = "Updates the status of an existing order")
    @ApiResponse(responseCode = "200", description = "Order status updated successfully")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
        @Parameter(description = "ID of the order") @PathVariable Long orderId,
        @Parameter(description = "New status for the order") @RequestParam OrderStatus status
    ) {
        return orderService.updateOrderStatus(orderId, status);
    }
} 