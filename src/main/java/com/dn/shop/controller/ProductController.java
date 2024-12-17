package com.dn.shop.controller;

import com.dn.shop.model.dto.product.EditProductDTO;
import com.dn.shop.model.dto.product.CreateProductDTO;
import com.dn.shop.model.entity.Product;
import com.dn.shop.service.ProductService;
import com.dn.shop.exception.ServiceResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(
    path = "/api/v1/products",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Product Controller", description = "APIs for product management")
@RequiredArgsConstructor
@Log4j2
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all available products")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved products")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.debug("Fetching products page {} with size {}", page, size);
        Page<Product> products = productService.getAllProducts(PageRequest.of(page, size));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ServiceResponse<Product>> getProductById(
            @PathVariable(name = "id") 
            @Positive(message = "Product ID must be a positive number") 
            Long id) {
        log.debug("Fetching product with ID: {}", id);
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ServiceResponse.success(product));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new product", description = "Creates a new product")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Product created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Product> addProduct(
            @Valid 
            @RequestBody(required = true) 
            CreateProductDTO newProduct) {
        log.info("Adding new product: {}", newProduct);
        Product created = productService.add(newProduct);
        return ResponseEntity
            .created(URI.create("/api/v1/products/" + created.getId()))
            .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Product> updateProduct(
            @PathVariable @Positive Long id,
            @Valid @RequestBody EditProductDTO updatedProduct) {
        log.info("Updating product with ID {} with new details: {}", id, updatedProduct);
        Product updated = productService.editProduct(id, updatedProduct);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes an existing product")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable @Positive Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
