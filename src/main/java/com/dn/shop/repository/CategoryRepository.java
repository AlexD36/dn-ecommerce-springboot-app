package com.dn.shop.repository;

import com.dn.shop.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Method to find a category by name
    Optional<Category> findByName(String name);
} 