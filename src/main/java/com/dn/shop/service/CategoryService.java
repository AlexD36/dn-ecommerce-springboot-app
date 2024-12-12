package com.dn.shop.service;

import com.dn.shop.model.entity.Category;
import com.dn.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Method to fetch all categories
    public List<Category> getAllCategories() {
        return StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    // Method to create a new category
    public ResponseEntity<String> createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            return ResponseEntity.badRequest().body("Category already exists!");
        }
        categoryRepository.save(category);
        return ResponseEntity.ok("Category added successfully!");
    }
} 