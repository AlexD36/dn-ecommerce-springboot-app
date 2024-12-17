package com.dn.shop.controller;
import com.dn.shop.model.dto.cart.CartDTO;
import com.dn.shop.model.dto.cart.AddToCartDTO;
import com.dn.shop.model.dto.user.EmailUpdateDTO;
import com.dn.shop.model.dto.user.GetUserDTO;
import com.dn.shop.model.dto.user.LoginRequestDTO;
import com.dn.shop.model.dto.user.RegisterUserDTO;
import com.dn.shop.model.entity.User;
import com.dn.shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dn.shop.util.JwtUtil;
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
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        if (loginRequest == null) {
            return ResponseEntity.badRequest().body("Login credentials must not be null.");
        }
        ResponseEntity<String> response = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (response.getStatusCode().is2xxSuccessful()) {
            String token = jwtUtil.generateToken(loginRequest.getEmail());
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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GetUserDTO>> getUsers() {
        List<GetUserDTO> users = userService.getUsers()
                .stream()
                .map(user -> objectMapper.convertValue(user, GetUserDTO.class))
                .toList();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        if (email == null) {
            return ResponseEntity.badRequest().body("Email must not be null.");
        }
        return userService.deleteUserByEmail(email);
    }

    @PutMapping("/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateUserEmail(
        @PathVariable String email,
        @RequestBody EmailUpdateDTO emailUpdate
    ) {
        if (email == null || emailUpdate.getNewEmail() == null) {
            return ResponseEntity.badRequest().body("Email must not be null.");
        }
        return userService.updateUserEmail(email, emailUpdate.getNewEmail());
    }

    @DeleteMapping("/{userId}/cart/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> removeFromCart(
        @PathVariable Long userId,
        @PathVariable Long productId
    ) {
        if (userId == null || productId == null) {
            return ResponseEntity.badRequest().body("User ID and Product ID must not be null.");
        }
        return userService.removeProductFromUserCart(userId, productId);
    }

    @PostMapping("/{userId}/cart")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> addToCart(
        @PathVariable Long userId,
        @RequestBody AddToCartDTO request
    ) {
        if (userId == null || request.getProductName() == null) {
            return ResponseEntity.badRequest().body("User ID and Product Name must not be null.");
        }
        return userService.addProductToUserCart(userId, request.getProductName());
    }

    @PostMapping("/addCart")
    public ResponseEntity<String> addCart(@RequestBody CartDTO cartDTO, @RequestParam Long userId) {
        if (cartDTO == null || userId == null) {
            return ResponseEntity.badRequest().body("Cart data and User ID must not be null.");
        }
        return userService.addCart(cartDTO, userId);
    }

}
