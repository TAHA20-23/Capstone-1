package com.example.capstone1.Controller;


import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/va/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    // 1. Get all Merchants
    @GetMapping("/get")
    public ResponseEntity getAllMerchants() {
        ArrayList<Merchant> merchants = merchantService.getAllMerchants();
        return ResponseEntity.status(HttpStatus.OK).body(merchants);
    }

    // 2. Add a Merchant
    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isAdded = merchantService.addMerchant(merchant);
        if (!isAdded) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant with this ID already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Merchant added successfully.");
    }

    // 3. Update a Merchant
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable int id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isUpdated = merchantService.updateMerchant(id, merchant);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Merchant updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant not found.");
    }

    // 4. Delete a Merchant
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable int id) {
        boolean isDeleted = merchantService.deleteMerchant(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Merchant deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant not found.");
    }

    // 5. Get Merchant by ID
    @GetMapping("/get/{id}")
    public ResponseEntity getMerchantById(@PathVariable int id) {
        Merchant merchant = merchantService.getMerchantById(id);
        if (merchant != null) {
            return ResponseEntity.status(HttpStatus.OK).body(merchant);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant not found.");
    }

}
