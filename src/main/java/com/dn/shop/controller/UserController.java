package com.dn.shop.controller;
import com.dn.shop.model.dto.AddUserDTO;
import com.dn.shop.model.dto.GetUserDTO;
import com.dn.shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody AddUserDTO addUserDTO){
        return userService.addUser(addUserDTO);
    }
    @GetMapping("/getUsers")
    public List<GetUserDTO> getUsers(){
       return userService.getUsers()
               .stream()
               .map(user -> objectMapper.convertValue(user , GetUserDTO.class))
               .toList();
    }

    @DeleteMapping("/deleteUserByEmail")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam("email") String email){
        return userService.deleteUserByEmail(email);
    }

    @PutMapping("/updateUserEmail")
    public ResponseEntity<String> updateUserEmail(@RequestParam("oldmail") String oldmail , @RequestParam("newmail") String newmail){
        return userService.updateUserEmail(oldmail,newmail);
    }

   @Transactional
   @DeleteMapping("/deleteProductFromUserBasket")
   public ResponseEntity<String> deleteProduct(@RequestParam("userID") Long userID , @RequestParam("productId") Long productId){
        return userService.removeProductFromUserBasket(userID,productId);
    }

    @Transactional
   @PostMapping("/addProductsToBasket")
    public ResponseEntity<String> addProductsToUserBasket(@RequestParam("userID") Long userID , @RequestParam("productName") String productName){
        return userService.addProductToUserBasket(userID,productName);
    }

}
