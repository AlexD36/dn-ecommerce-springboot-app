package com.dn.shop.controller;

import com.dn.shop.model.entity.Category;
import com.dn.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Endpoint to fetch all categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Endpoint to add a new category
    @PostMapping
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }
} 