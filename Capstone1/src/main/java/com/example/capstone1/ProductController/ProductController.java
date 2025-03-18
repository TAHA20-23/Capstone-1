package com.example.capstone1.ProductController;


import com.example.capstone1.ProductComparisonResponseModel.ProductComparisonResponse;
import com.example.capstone1.ProductModel.Product;
import com.example.capstone1.ProductService.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/va/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 1. Get all Products
    @GetMapping("/get")
    public ResponseEntity getAllProducts() {
        ArrayList<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    // 2. Add a Product
    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isAdded = productService.addProduct(product);
        if (!isAdded) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product with this ID already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully.");
    }

    // 3. Update a Product
    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable int id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isUpdated = productService.updateProduct(id, product);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }

    // 4. Delete a Product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }

    // 5. Search a Product by ID
    @GetMapping("/get/{id}")
    public ResponseEntity getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }



    // Extra 1
    //=======================================================================
    // 6. Compare two products
    @GetMapping("/compare/{productId1}/{productId2}")
    public ResponseEntity compareProducts(@PathVariable int productId1, @PathVariable int productId2) {

        //
        Product product1 = productService.getProductById(productId1);
        Product product2 = productService.getProductById(productId2);

        if (product1 == null || product2 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or both products not found.");
        }

        // إرسال المنتجين للمقارنة
        return ResponseEntity.status(HttpStatus.OK).body(new ProductComparisonResponse(product1, product2));
    }

    // Extra 2
    //============================================================
    // 1. Get products by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity getProductsByCategory(@PathVariable int categoryId) {
        ArrayList<Product> products = productService.getProductsByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found in this category.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    //Extra 3
    //======================
    // 1. Add ratings to a product
    @PostMapping("/{productId}/ratings")
    public ResponseEntity<String> addRatings(@PathVariable int productId, @RequestBody ArrayList<Integer> ratings) {
        // Get the product by ID from the ProductService
        Product product = productService.getProductById(productId);

        // Check if the product exists
        if (product == null) {
            return ResponseEntity.badRequest().body("Product not found.");
        }

        // Call the service to add ratings
        boolean isAdded = productService.addRatingsToProduct(product, ratings);
        if (!isAdded) {
            return ResponseEntity.status(400).body("Failed to add ratings.");
        }

        return ResponseEntity.status(200).body("Ratings added successfully.");
    }

    // 2. Get the average rating of a product
    @GetMapping("/{productId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable int productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();  // Product not found
        }

        double averageRating = productService.calculateAverageRating(product);
        return ResponseEntity.status(200).body(averageRating);  // Return average rating
    }

    //Extra 4
    // 1. Add a Product with Validation
    @PostMapping("/toggle-favorite/{id}")
    public ResponseEntity<String> toggleFavorite(@PathVariable int id) {
        boolean success = productService.toggleFavorite(id);
        if (success) {
            return ResponseEntity.status(200).body("Favorite status toggled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    // 3. Get All Favorite Products
    @GetMapping("/favorites")
    public ResponseEntity<ArrayList<Product>> getFavoriteProducts() {
        ArrayList<Product> favoriteProducts = productService.getFavoriteProducts();
        return ResponseEntity.status(200).body(favoriteProducts);
    }


}
