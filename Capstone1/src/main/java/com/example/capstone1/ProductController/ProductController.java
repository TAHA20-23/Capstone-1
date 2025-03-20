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

import java.util.List;

@RestController
@RequestMapping("/api/va/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //  Get all Products
    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    //  Add a Product
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        }

        boolean isAdded = productService.addProduct(product);
        if (!isAdded) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product with this ID already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully.");
    }

    //  Update a Product
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated = productService.updateProduct(id, product);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }

    //  Delete a Product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }

    //  Search a Product by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    //  Add a rating to a product
    @PostMapping("/rate/{productId}")
    public ResponseEntity<String> addRating(@PathVariable int productId, @RequestParam int rating) {
        boolean isRated = productService.addRating(productId, rating);
        if (!isRated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID or rating (must be between 1 and 5).");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Rating added successfully.");
    }

    //  Compare two products
    @GetMapping("/compare/{productId1}/{productId2}")
    public ResponseEntity<?> compareProducts(@PathVariable int productId1, @PathVariable int productId2) {
        Product product1 = productService.getProductById(productId1);
        Product product2 = productService.getProductById(productId2);

        if (product1 == null || product2 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or both products not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ProductComparisonResponse(product1, product2));
    }
}