package com.dn.shop.service;

import com.dn.shop.model.dto.cart.CartDTO;
import com.dn.shop.model.dto.user.RegisterUserDTO;
import com.dn.shop.model.entity.Product;
import com.dn.shop.model.entity.User;
import com.dn.shop.repository.ProductRepository;
import com.dn.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> addUser(RegisterUserDTO registerUserDTO){
        // Validate input
        if (registerUserDTO == null || registerUserDTO.getEmail() == null || registerUserDTO.getPassword() == null) {
            return ResponseEntity.badRequest().body("Invalid user data!");
        }

        User userToBeSaved = User.builder()
                .firstName(registerUserDTO.getFirstName().toLowerCase())
                .lastName(registerUserDTO.getLastName().toLowerCase())
                .email(registerUserDTO.getEmail().toLowerCase())
                .password(passwordEncoder.encode(registerUserDTO.getPassword()))
                .basket(registerUserDTO.getCartDTO() != null ? registerUserDTO.getCartDTO().getCart() : null) // Handle potential null cartDTO
                .build();
       if(userRepository.count() == 0){
            userRepository.save(userToBeSaved);
            return ResponseEntity.accepted().body("Added Succesfully!");
        }

       if(userRepository.checkIfUserExists(registerUserDTO.getFirstName().toLowerCase(), registerUserDTO.getLastName().toLowerCase(), registerUserDTO.getEmail().toLowerCase())){
            return ResponseEntity.badRequest().body("User already exists!");
        }
        userRepository.save(userToBeSaved);
        return ResponseEntity.accepted().body("User Added!");
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<String> deleteUserByEmail(String email){
        // Validate email input
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be null or empty!");
        }

        if(userRepository.findUserByEmail(email.toLowerCase())){
            userRepository.deleteUserByEmail(email.toLowerCase());
            return ResponseEntity.accepted()
                    .body(MessageFormat.format("User with the email {0} deleted succesfully!" ,email));
        }
        return ResponseEntity.badRequest()
                .body(MessageFormat.format("User with the email {0} not found" ,email));
    }

    public ResponseEntity<String> updateUserEmail(String oldmail , String newmail){
        // Validate email inputs
        if (oldmail == null || newmail == null || oldmail.isEmpty() || newmail.isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be null or empty!");
        }

        if(userRepository.count() == 0){
            return ResponseEntity.noContent().build();
        }
        if(userRepository.findUserByEmail(oldmail.toLowerCase())){
            userRepository.updateUserEmail(oldmail.toLowerCase(),newmail.toLowerCase());
            return ResponseEntity.accepted()
                    .body("Email changed succesfully!");
        }

        return ResponseEntity.badRequest()
                .body(MessageFormat.format("User with the email {0} was not found or there was a typo!" ,oldmail));
    }




   public ResponseEntity<String> addProductToUserCart(Long uId , String productName){
        // Validate inputs
        if (uId == null || productName == null || productName.isEmpty()) {
            return ResponseEntity.badRequest().body("User ID and product name cannot be null or empty!");
        }

        if(userRepository.count() == 0 || productRepository.count() == 0){
            return ResponseEntity.noContent().build();
        }
        if(userRepository.findById(uId).isPresent() && productRepository.findByName(productName).isPresent()){
            userRepository.findById(uId).get().getBasket().add(productRepository.findByName(productName).get());
            return ResponseEntity.ok("Added Succesfully");
        }
        return ResponseEntity.badRequest().body("Something went wrong");


    }

    public ResponseEntity<String> removeProductFromUserCart(Long userID , Long productId){
        // Validate inputs
        if (userID == null || productId == null) {
            return ResponseEntity.badRequest().body("User ID and product ID cannot be null!");
        }

        Optional<User> userOptional = userRepository.findById(userID);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User not found!");
        }

        User user = userOptional.get();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!user.getBasket().contains(productOptional.orElse(null)) || user.getBasket().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty or the product was not found!");
        }
        user.getBasket().remove(productOptional.get());
        return ResponseEntity.ok("Product Deleted from the cart");
    }

    public ResponseEntity<String> registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    public ResponseEntity<String> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.ok("Login successful!");
            } else {
                return ResponseEntity.badRequest().body("Invalid password!");
            }
        }
        return ResponseEntity.badRequest().body("User not found!");
    }

    public ResponseEntity<User> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> updateUser(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.save(user);
            return ResponseEntity.ok("User details updated successfully!");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> addCart(CartDTO cartDTO, Long userId) {
        // Validate inputs
        if (cartDTO == null || userId == null) {
            return ResponseEntity.badRequest().body("Cart data and user ID cannot be null!");
        }

        // Check if the user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User not found!");
        }

        // Associate the cart with the user
        User user = userOptional.get();
        user.setBasket(cartDTO.getCart()); // Using basket instead of cart

        // Save the updated user with the new basket
        userRepository.save(user);
        return ResponseEntity.ok("Cart added successfully!");
    }
}
