package com.example.capstone1.ProductComparisonResponseModel;

import com.example.capstone1.ProductModel.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductComparisonResponse {


    private Product product1;
    private Product product2;

}
