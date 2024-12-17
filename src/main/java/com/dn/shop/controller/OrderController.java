package com.dn.shop.controller;

import com.dn.shop.model.dto.order.OrderDTO;
import com.dn.shop.model.entity.OrderStatus;
import com.dn.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
@Log4j2
@Tag(name = "Order Management", description = "Endpoints for managing orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order", 
               description = "Creates a new order with the provided details")
    @ApiResponse(responseCode = "201", description = "Order created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid order data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
        @Parameter(description = "Order details", required = true) 
        @Valid @RequestBody OrderDTO orderDTO
    ) {
        log.info("Creating new order for user: {}", orderDTO.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(orderService.createOrder(orderDTO));
    }

    @Operation(summary = "Get orders by user", 
               description = "Retrieves all orders for a specific user")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(
        @Parameter(description = "ID of the user", required = true) 
        @PathVariable @NotNull @Positive Long userId
    ) {
        log.info("Fetching orders for user: {}", userId);
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @Operation(summary = "Update order status", 
               description = "Updates the status of an existing order")
    @ApiResponse(responseCode = "200", description = "Order status updated successfully")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @ApiResponse(responseCode = "400", description = "Invalid status value")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
        @Parameter(description = "ID of the order", required = true) 
        @PathVariable @NotNull @Positive Long orderId,
        @Parameter(description = "New status for the order", required = true) 
        @RequestParam @NotNull OrderStatus status
    ) {
        log.info("Updating order {} status to: {}", orderId, status);
        // Consider adding try-catch block for better error handling
        try {
            return orderService.updateOrderStatus(orderId, status);
        } catch (EntityNotFoundException e) {
            log.error("Order not found: {}", orderId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating order status", e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 