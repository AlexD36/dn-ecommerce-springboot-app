package com.dn.shop.service;

import com.dn.shop.model.dto.product.EditProductDTO;
import com.dn.shop.model.entity.Product;
import com.dn.shop.repository.ProductRepository;
import com.dn.shop.repository.UserRepository;
import com.dn.shop.model.dto.product.CreateProductDTO;
import com.dn.shop.model.entity.Category;
import com.dn.shop.repository.CategoryRepository;
import com.dn.shop.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
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

    public Product add(CreateProductDTO newProduct) {
        Category category = categoryRepository.findById(newProduct.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (productRepository.findByName(newProduct.getName()).isPresent()) {
            throw new IllegalArgumentException("Product already exists!");
        }

        Product product = new Product(
                newProduct.getName().toLowerCase(),
                newProduct.getDescription().toLowerCase(),
                newProduct.getPrice(),
                newProduct.getStock(),
                category
        );
        
        return productRepository.save(product);
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

    public Product editProduct(Long id, EditProductDTO newProduct) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        Optional<Product> existingProductWithName = productRepository.findByName(newProduct.getName());
        if (existingProductWithName.isPresent() && !existingProductWithName.get().getId().equals(id)) {
            throw new IllegalArgumentException("Another product with the same name already exists!");
        }
        
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setStock(newProduct.getStock());
        
        return productRepository.save(product);
    }

    public Page<Product> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }
}
