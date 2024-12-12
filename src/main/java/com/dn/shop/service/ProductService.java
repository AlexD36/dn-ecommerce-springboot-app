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

    public ResponseEntity<String> deleteProductById(Long id){
        if(userRepository.findAll().stream().anyMatch(user -> user.getBasket().contains(productRepository.findById(id).get()))){
            return ResponseEntity.badRequest().body("Product is into the user basket");
        }
        if(productRepository.findById(id).isPresent()){
            productRepository.delete(productRepository.findById(id).get());
            return ResponseEntity.ok("Deleted succesfully!");
        }
        if(productRepository.count() == 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Product not found");
    }

    public boolean deleteProductByName(String name) throws NoSuchFieldException {
        log.info("Delete request for productID {}", name);
        Optional<Product> findByName = productRepository.findByName(name);

        if(userRepository.findAll().stream().anyMatch(user -> user.getBasket().contains(findByName.get()))){
            log.info("The product is into an user basket");
            return false;
        }

        if (productRepository.count() == 0){
            log.warn("ID {} not found in DB. Nothing to delete.",name);
            return false;
        }
        if(findByName.isPresent()){
            productRepository.delete(productRepository.findByName(name).get());
            return true;
        }

        return false;

    }

    public ResponseEntity<String> editProduct(Long id, EditProductDTO newProduct){
        log.info("edit request for productID {}", id);
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        if(byId.isPresent()){
            return ResponseEntity.badRequest().body("Another product with the same name already exists!");
        }

        byId.get().setName(newProduct.getName());
        byId.get().setDescription(newProduct.getDescription());
        productRepository.save(byId.get());
        return ResponseEntity.ok("Edited succesfully!");
    }
}
