package com.example.capstone1.MerchantStockModel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotNull(message = "Id can't be null")
    private int id;

    @NotNull(message = "ProductId can't be empty")
    private int productId;

    @NotNull(message = "MerchantId can't be empty")
    private int merchantId;

    @Min(value = 10, message = "Stock must be greater than or equal to 10 at start")
    private int stock;
}
