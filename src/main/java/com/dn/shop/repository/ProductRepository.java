package com.dn.shop.repository;

import com.dn.shop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    // New method to find products by category ID
    List<Product> findByCategory_Id(Long categoryId);

    // New method to search for products by name containing a keyword
    List<Product> findByNameContaining(String keyword);
}
