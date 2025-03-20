package com.example.capstone1.Controller;


import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/va/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 1. Get all Categories
    @GetMapping("/get")
    public ResponseEntity getAllCategories() {
        ArrayList<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    // 2. Add a Category
    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isAdded = categoryService.addCategory(category);
        if (!isAdded) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category with this ID already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Category added successfully.");
    }

    // 3. Update a Category
    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable int id, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isUpdated = categoryService.updateCategory(id, category);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Category updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
    }

    // 4. Delete a Category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable int id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
    }

    // 5. Get Category by ID
    @GetMapping("/get/{id}")
    public ResponseEntity getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.status(HttpStatus.OK).body(category);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
        }
    }
}
