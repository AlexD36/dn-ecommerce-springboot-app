package com.dn.shop.repository;

import com.dn.shop.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Method to find orders by user ID
    List<Order> findByUser_Id(Long userId);
} 