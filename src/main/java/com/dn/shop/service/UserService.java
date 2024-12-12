package com.dn.shop.service;
import com.dn.shop.model.dto.cart.CartDTO;
import com.dn.shop.model.dto.cart.UpdateCartItemDTO;
import com.dn.shop.model.dto.user.AddUserDTO;
import com.dn.shop.model.entity.User;
import com.dn.shop.repository.ProductRepository;
import com.dn.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<String> addUser(AddUserDTO addUserDTO){

        User userToBeSaved = User.builder()
                .firstName(addUserDTO.getFirst_name().toLowerCase())
                .lastName(addUserDTO.getLast_name().toLowerCase())
                .email(addUserDTO.getEmail().toLowerCase())
                .password(passwordEncoder.encode(addUserDTO.getPassword()))
                .cart(addUserDTO.getCartDTO().getCart())
                .build();
       if(userRepository.count() == 0){
            userRepository.save(userToBeSaved);
            return ResponseEntity.accepted().body("Added Succesfully!");
        }

       if(userRepository.checkIfUserExists(addUserDTO.getFirst_name().toLowerCase(), addUserDTO.getLast_name().toLowerCase(), addUserDTO.getEmail().toLowerCase())){
            return ResponseEntity.badRequest().body("User already exists!");
        }
        userRepository.save(userToBeSaved);
        return ResponseEntity.accepted().body("User Added!");
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<String> deleteUserByEmail(String email){
        if(userRepository.findUserByEmail(email.toLowerCase())){
            userRepository.deleteUserByEmail(email.toLowerCase());
            return ResponseEntity.accepted()
                    .body(MessageFormat.format("User with the email {0} deleted succesfully!" ,email));
        }
        return ResponseEntity.badRequest()
                .body(MessageFormat.format("User with the email {0} not found" ,email));
    }

    public ResponseEntity<String> updateUserEmail(String oldmail , String newmail){
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
        if(userRepository.count() == 0 || productRepository.count() == 0){
            return ResponseEntity.noContent().build();
        }
        if(userRepository.findById(uId).isPresent() && productRepository.findByName(productName).isPresent()){
            userRepository.findById(uId).get().getCart().add(productRepository.findByName(productName).get());
            return ResponseEntity.ok("Added Succesfully");
        }
        return ResponseEntity.badRequest().body("Something went wrong");


    }

    public ResponseEntity<String> removeProductFromUserCart(Long userID , Long productId){
        if(!userRepository.findById(userID).isPresent()){
            return ResponseEntity.badRequest().body("User not found!");
        }

        if(!userRepository.findById(userID).get().getCart().contains(productRepository.findById(productId).get()) || userRepository.findById(userID).get().getCart().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty or the product was not found!");

        }
        userRepository.findById(userID).get().getCart().remove(productRepository.findById(productId).get());
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
        // Check if the user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User not found!");
        }

        // Associate the cart with the user
        User user = userOptional.get();
        user.setCart(cartDTO.getCart()); // Assuming CartDTO has a method to get the cart

        // Save the updated user with the new cart
        userRepository.save(user);
        return ResponseEntity.ok("Cart added successfully!");
    }
}
