package com.example.capstone1.UserChangePasswordModell;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordChangeRequest {

    @NotEmpty(message = "Password can't be empty")
    @Size(min = 6, message = "Password must be more than 6 characters long")
    private String oldPassword;
    @NotEmpty(message = "Password can't be empty")
    @Size(min = 6, message = "Password must be more than 6 characters long")
    private String newPassword;

}
