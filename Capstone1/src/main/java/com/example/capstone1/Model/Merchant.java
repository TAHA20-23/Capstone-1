package com.example.capstone1.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotNull(message = "Id can't be null")
    private int id;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = 3, message = "Name must be more than 3 characters long")
    private String name;

    @NotNull(message = "Id can't be null")
    private int productId;

    @NotNull(message = "Id can't be null")
    private int merchantId;

    @NotNull(message = "Id can't be null")
    private int stock;




//    {
//  "id": 1,
//  "name": "Merchant1"
//}




}
