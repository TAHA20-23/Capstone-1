package com.example.capstone1.Controller;

import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import com.example.capstone1.Model.PasswordChangeRequest;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    //Extra 1
    //================================================================================
    // Endpoint to handle the purchase of a product
    @PostMapping("/buy")
    public ResponseEntity<String> buyProduct(@RequestParam int userId, @RequestParam int productId, @RequestParam int merchantId, @RequestParam int quantity) {

        // Validate user existence
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
        }

        // Validate product existence
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found.");
        }

        // Validate merchant stock for the specified product
        MerchantStock merchantStock = merchantStockService.getMerchantStockByProductAndMerchant(productId, merchantId);
        if (merchantStock == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant does not have this product.");
        }

        // Check if the merchant has enough stock
        if (merchantStock.getStock() < quantity) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock.");
        }

        // Calculate total price
        double totalPrice = product.getPrice() * quantity;

        // Check if user has enough balance
        if (user.getBalance() < totalPrice) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance.");
        }

        // Deduct stock from the merchant
        boolean stockReduced = merchantStockService.reduceStock(productId, merchantId, quantity);
        if (!stockReduced) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in stock reduction.");
        }

        // Deduct balance from the user
        user.setBalance(user.getBalance() - totalPrice);

        // Update user and merchant stock
        userService.updateUser(userId, user);
        merchantStockService.updateMerchantStock(merchantId, merchantStock);

        return ResponseEntity.status(HttpStatus.OK).body("Product purchased successfully.");
    }

    // Extra Features
    // ===============================================
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

    //EXtar5
    //================================

    // Add product to wishlist
    @PostMapping("/{userId}/wishlist/add/{productId}")
    public ResponseEntity<String> addToWishlist(@PathVariable int userId, @PathVariable int productId) {
        boolean added = userService.addToWishlist(userId, productId);
        if (added) {
            return ResponseEntity.status(HttpStatus.OK).body("Product added to wishlist.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is already in wishlist or user not found.");
    }

    //  Remove product from wishlist
    @DeleteMapping("/{userId}/wishlist/remove/{productId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable int userId, @PathVariable int productId) {
        boolean removed = userService.removeFromWishlist(userId, productId);
        if (removed) {
            return ResponseEntity.status(HttpStatus.OK).body("Product removed from wishlist.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in wishlist or user does not exist.");
    }

    // Get user's wishlist
    @GetMapping("/{userId}/wishlist")
    public ResponseEntity<ArrayList<Integer>> getWishlist(@PathVariable int userId) {
        ArrayList<Integer> wishlist = userService.getWishlist(userId);
        if (wishlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(wishlist);
    }

}