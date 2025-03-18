package com.example.capstone1.MerchantStockController;

import com.example.capstone1.MerchantStockModel.MerchantStock;
import com.example.capstone1.MerchantStockService.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/va/merchant-stocks")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    // 1. Get all Merchant Stocks
    @GetMapping("/get")
    public ResponseEntity getAllMerchantStocks() {
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getAllMerchantStocks();
        return ResponseEntity.status(HttpStatus.OK).body(merchantStocks);
    }

    // 2. Add a Merchant Stock
    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isAdded = merchantStockService.addMerchantStock(merchantStock);
        if (!isAdded) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant Stock with this ID already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Merchant Stock added successfully.");
    }

    // 3. Update a Merchant Stock
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable int id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Merchant Stock updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant Stock not found.");
    }

    // 4. Delete a Merchant Stock
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable int id) {
        boolean isDeleted = merchantStockService.deleteMerchantStock(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Merchant Stock deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant Stock not found.");
    }

    // 5. Search Merchant Stock by ID
    @GetMapping("/get/{id}")
    public ResponseEntity getMerchantStockById(@PathVariable int id) {
        MerchantStock merchantStock = merchantStockService.getMerchantStockById(id);
        if (merchantStock == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant Stock not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(merchantStock);
    }

    //--------------------------------------------------------------------------------

    // 6. Add more stocks to a Merchant Stock
    @PutMapping("/add-stock")
    public ResponseEntity addStockToMerchant(@RequestParam int productId, @RequestParam int merchantId, @RequestParam int additionalStock) {
        boolean isUpdated = merchantStockService.addStock(productId, merchantId, additionalStock);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Stock updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product or Merchant not found.");
    }

    // 7. Reduce stock
    @PostMapping("/{productId}/{merchantId}/reduce-stock")
    public ResponseEntity<String> reduceStock(@PathVariable int productId,@PathVariable int merchantId,@RequestParam int quantity) {

        boolean isReduced = merchantStockService.reduceStock(productId, merchantId, quantity);
        if (isReduced) {
            return ResponseEntity.status(HttpStatus.OK).body("Stock reduced successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough stock or invalid product/merchant.");
        }
    }
}
