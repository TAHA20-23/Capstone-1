package com.example.capstone1.UserModel;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class User {

    @NotNull(message = "Id can't be null")
    private int id;

    @NotEmpty(message = "Username can't be empty")
    @Size(min = 5, message = "Username must be more than 5 characters long")
    private String username;

//    @NotEmpty(message = " New User Name can not be empty ")
//    private String newUserName;


    @NotEmpty(message = "Password can't be empty")
    @Size(min = 6, message = "Password must be more than 6 characters long")
    private String password;

    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Role can't be empty")
    private String role;

    @Min(value = 0, message = "Balance must be positive")
    private double balance;
}
