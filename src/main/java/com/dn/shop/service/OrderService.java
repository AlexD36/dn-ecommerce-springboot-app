package com.dn.shop.service;

import com.dn.shop.model.entity.Order;
import com.dn.shop.model.entity.OrderStatus;
import com.dn.shop.model.dto.order.OrderDTO;
import com.dn.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing orders in the shop system.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Converts Order entity to OrderDTO
     *
     * @param order the order entity to convert
     * @return OrderDTO representation of the order
     */
    private OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder()
            .id(order.getId())
            .userId(order.getUser().getId())
            .status(order.getStatus())
            .totalPrice(order.getTotalPrice())
            .createdAt(order.getCreatedAt())
            .build();
    }

    /**
     * Creates a new order in the system.
     *
     * @param orderDTO the order data transfer object
     * @return OrderDTO containing created order details
     */
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Assert.notNull(orderDTO, "OrderDTO cannot be null");
        try {
            // Implementation here
            return orderDTO;
        } catch (Exception e) {
            log.error("Error creating order: ", e);
            throw new RuntimeException("Error creating order: " + e.getMessage());
        }
    }

    /**
     * Retrieves all orders for a specific user.
     *
     * @param userId the ID of the user
     * @return List of OrderDTO objects
     * @throws IllegalArgumentException if userId is null
     */
    public List<OrderDTO> getOrdersByUser(Long userId) {
        Assert.notNull(userId, "User ID cannot be null");
        try {
            List<Order> orders = orderRepository.findByUser_Id(userId);
            return orders.stream()
                .map(this::convertToDTO)
                .toList();
        } catch (Exception e) {
            log.error("Error fetching orders for user {}: ", userId, e);
            throw new RuntimeException("Error fetching orders: " + e.getMessage());
        }
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId the ID of the order to update
     * @param status the new status to set
     * @return ResponseEntity containing success message or error details
     * @throws IllegalArgumentException if orderId or status is null
     */
    public ResponseEntity<OrderDTO> updateOrderStatus(Long orderId, OrderStatus status) {
        Assert.notNull(orderId, "Order ID cannot be null");
        Assert.notNull(status, "Status cannot be null");

        try {
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                order.setStatus(status);
                order = orderRepository.save(order);
                log.info("Order {} status updated to: {}", orderId, status);
                return ResponseEntity.ok(convertToDTO(order));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating order status: ", e);
            throw new RuntimeException("Error updating order status: " + e.getMessage());
        }
    }
} 