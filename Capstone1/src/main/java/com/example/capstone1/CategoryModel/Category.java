package com.example.capstone1.CategoryModel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Category {


    @NotNull(message = "Id can't be null")
    private int id;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = 3, message = "Name must be more than 3 characters long")
    private String name;
}
