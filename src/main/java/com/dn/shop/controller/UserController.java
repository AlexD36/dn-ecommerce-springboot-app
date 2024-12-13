package com.dn.shop.controller;
import com.dn.shop.model.dto.cart.CartDTO;
import com.dn.shop.model.dto.user.GetUserDTO;
import com.dn.shop.model.dto.user.RegisterUserDTO;
import com.dn.shop.model.entity.User;
import com.dn.shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dn.shop.util.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        if (registerUserDTO == null || registerUserDTO.getEmail() == null || registerUserDTO.getPassword() == null) {
            return ResponseEntity.badRequest().body("Invalid registration data.");
        }
        return userService.addUser(registerUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password must not be null.");
        }
        ResponseEntity<String> response = userService.login(email, password);
        if (response.getStatusCode().is2xxSuccessful()) {
            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok(token);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<User> userResponse = userService.getUserById(id);
        if (!userResponse.getStatusCode().is2xxSuccessful() || userResponse.getBody() == null) {
            return ResponseEntity.notFound().build();
        }
        GetUserDTO userDTO = objectMapper.convertValue(userResponse.getBody(), GetUserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GetUserDTO>> getUsers() {
        List<GetUserDTO> users = userService.getUsers()
                .stream()
                .map(user -> objectMapper.convertValue(user, GetUserDTO.class))
                .toList();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/deleteUserByEmail")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam("email") String email){
        if (email == null) {
            return ResponseEntity.badRequest().body("Email must not be null.");
        }
        return userService.deleteUserByEmail(email);
    }

    @PutMapping("/updateUserEmail")
    public ResponseEntity<String> updateUserEmail(@RequestParam("oldmail") String oldmail , @RequestParam("newmail") String newmail){
        if (oldmail == null || newmail == null) {
            return ResponseEntity.badRequest().body("Old and new email must not be null.");
        }
        return userService.updateUserEmail(oldmail,newmail);
    }

   @Transactional
   @DeleteMapping("/deleteProductFromUserCart")
   public ResponseEntity<String> deleteProduct(@RequestParam("userID") Long userID , @RequestParam("productId") Long productId){
        if (userID == null || productId == null) {
            return ResponseEntity.badRequest().body("User ID and Product ID must not be null.");
        }
        return userService.removeProductFromUserCart(userID,productId);
    }

    @Transactional
   @PostMapping("/addProductsToCart")
    public ResponseEntity<String> addProductsToUserCart(@RequestParam("userID") Long userID , @RequestParam("productName") String productName){
        if (userID == null || productName == null) {
            return ResponseEntity.badRequest().body("User ID and Product Name must not be null.");
        }
        return userService.addProductToUserCart(userID,productName);
    }

    @PostMapping("/addCart")
    public ResponseEntity<String> addCart(@RequestBody CartDTO cartDTO, @RequestParam Long userId) {
        if (cartDTO == null || userId == null) {
            return ResponseEntity.badRequest().body("Cart data and User ID must not be null.");
        }
        return userService.addCart(cartDTO, userId);
    }

}
