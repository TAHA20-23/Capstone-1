package com.example.capstone1.UserController;


import com.example.capstone1.MerchantStockModel.MerchantStock;
import com.example.capstone1.MerchantStockService.MerchantStockService;
import com.example.capstone1.ProductModel.Product;
import com.example.capstone1.ProductService.ProductService;
import com.example.capstone1.UserChangePasswordModell.PasswordChangeRequest;
import com.example.capstone1.UserModel.User;
import com.example.capstone1.UserService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/va/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;

    // 1. Get all Users
    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        ArrayList<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    // 2. Add a User
    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isAdded = userService.addUser(user);
        if (!isAdded) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this ID already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully.");
    }

    // 3. Update a User
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable int id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    // 4. Delete a User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    // 5. Search User by ID
    @GetMapping("/get/{id}")
    public ResponseEntity getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    // Endpoint to handle the purchase of a product
    @PostMapping("/buy")
    public ResponseEntity<String> buyProduct(@RequestParam int userId, @RequestParam int productId, @RequestParam int merchantId, @RequestParam int quantity) {
        // Validate the user ID, product ID, and merchant ID
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(400).body("User not found.");
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(400).body("Product not found.");
        }

        MerchantStock merchantStock = merchantStockService.getMerchantStockById(merchantId);
        if (merchantStock == null || merchantStock.getProductId() != productId) {
            return ResponseEntity.status(400).body("Merchant does not have this product.");
        }

        // Check if the merchant has enough stock for the product
        if (merchantStock.getStock() < quantity) {
            return ResponseEntity.status(400).body("Insufficient stock.");
        }

        // Check if the user has enough balance to make the purchase
        if (user.getBalance() < product.getPrice() * quantity) {
            return ResponseEntity.status(400).body("Insufficient balance.");
        }

        // Deduct the stock from the merchant
        merchantStockService.addStock(productId, merchantId, -quantity);

        // Deduct the price from the user's balance
        user.setBalance(user.getBalance() - (product.getPrice() * quantity));

        // Save the updated user and merchant stock
        userService.updateUser(userId, user);
        merchantStockService.updateMerchantStock(merchantId, merchantStock);

        return ResponseEntity.status(200).body("Product purchased successfully.");
    }

    //Extra
    //===============================================
    @PutMapping("/change-username/{id}")
    public ResponseEntity<?> updateUsername(@PathVariable int id, @RequestBody String newUsername) {
        boolean isUsernameUpdated = userService.updateUsername(id, newUsername);
        if (isUsernameUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Username updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
    }

    // 7. Change User's Password
    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable int id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        boolean isPasswordChanged = userService.changePassword(id, passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
        if (isPasswordChanged) {
            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect.");
    }
}