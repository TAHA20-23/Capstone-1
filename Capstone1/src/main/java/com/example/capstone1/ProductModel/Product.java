package com.example.capstone1.ProductModel;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Product {

    @NotNull(message = "Id Can't be null")
    private int id;

    @NotEmpty(message = "Name can not be empty")
    @Size(min = 3, message = "Name must be more than 3 characters long")
    private String name;

    @NotNull(message = "Price can not be empty")
    @Min(value = 0, message = "Price must be greater than or equal to zero")
    private double price;

    @NotNull(message = "CategoryId Can't be null")
    private int categoryId;

    // Initialize ratings list to prevent NullPointerException
    private List<Integer> ratings ;

    private double averageRating ;
}