package com.dn.shop.service;

import com.dn.shop.model.dto.product.EditProductDTO;
import com.dn.shop.model.entity.Product;
import com.dn.shop.repository.ProductRepository;
import com.dn.shop.repository.UserRepository;
import com.dn.shop.model.dto.product.CreateProductDTO;
import com.dn.shop.model.entity.Category;
import com.dn.shop.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public ResponseEntity<Product> getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> createProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            return ResponseEntity.badRequest().body("Product already exists!");
        }
        productRepository.save(product);
        return ResponseEntity.ok("Product added!");
    }

    public ResponseEntity<String> updateProduct(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        productRepository.save(product);
        return ResponseEntity.ok("Product updated successfully!");
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }

    public ResponseEntity<String> add(CreateProductDTO newProduct) {
        BigDecimal defaultPrice = newProduct.getPrice();
        int defaultStock = newProduct.getStock();

        Category category = categoryRepository.findById(newProduct.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product toBeSaved = new Product(
                newProduct.getName().toLowerCase(),
                newProduct.getDescription().toLowerCase(),
                defaultPrice,
                defaultStock,
                category
        );
        if (productRepository.findByName(toBeSaved.getName()).isPresent()) {
            return ResponseEntity.badRequest().body("Product already exists!");
        }
        productRepository.save(toBeSaved);
        return ResponseEntity.ok("Product added!");
    }

    public ResponseEntity<String> deleteProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Product product = productOptional.get();
        boolean isInBasket = userRepository.findAll().stream()
                .anyMatch(user -> user.getBasket().contains(product));
                
        if (isInBasket) {
            return ResponseEntity.badRequest().body("Product is in a user's basket");
        }
        
        productRepository.delete(product);
        return ResponseEntity.ok("Deleted successfully!");
    }

    public boolean deleteProductByName(String name) {
        log.info("Delete request for product name: {}", name);
        Optional<Product> productOptional = productRepository.findByName(name);
        
        if (productOptional.isEmpty()) {
            log.warn("Product with name {} not found in DB. Nothing to delete.", name);
            return false;
        }
        
        Product product = productOptional.get();
        boolean isInBasket = userRepository.findAll().stream()
                .anyMatch(user -> user.getBasket().contains(product));
                
        if (isInBasket) {
            log.info("The product is in a user's basket");
            return false;
        }
        
        productRepository.delete(product);
        return true;
    }

    public ResponseEntity<String> editProduct(Long id, EditProductDTO newProduct) {
        log.info("Edit request for productID: {}", id);
        Optional<Product> productOptional = productRepository.findById(id);
        
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Product product = productOptional.get();
        Optional<Product> existingProductWithName = productRepository.findByName(newProduct.getName());
        
        if (existingProductWithName.isPresent() && !existingProductWithName.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Another product with the same name already exists!");
        }
        
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        if (newProduct.getPrice() != null) {
            product.setPrice(newProduct.getPrice());
        }
        product.setStock(newProduct.getStock());
        
        productRepository.save(product);
        return ResponseEntity.ok("Edited successfully!");
    }
}
