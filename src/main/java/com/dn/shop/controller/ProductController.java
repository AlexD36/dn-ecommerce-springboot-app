package com.dn.shop.controller;

import com.dn.shop.model.dto.product.EditProductDTO;
import com.dn.shop.model.dto.product.CreateProductDTO;
import com.dn.shop.model.entity.Product;
import com.dn.shop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper mapper;

    // Endpoint to fetch all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Endpoint to fetch a single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Endpoint to add a new product
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody CreateProductDTO newProduct) {
        log.info("Adding newly received product {}", newProduct);
        return productService.add(newProduct);
    }

    // Endpoint to update a product
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody EditProductDTO updatedProduct) {
        log.info("Updating product with ID {} with new details {}", id, updatedProduct);
        return productService.editProduct(id, updatedProduct);
    }

    // Endpoint to delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.deleteProductById(id);
    }
}
