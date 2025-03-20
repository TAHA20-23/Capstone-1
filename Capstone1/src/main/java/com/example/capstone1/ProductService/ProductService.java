package com.example.capstone1.ProductService;


import com.example.capstone1.ProductModel.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    private ArrayList<Product> products = new ArrayList<>();

    // 1. Get all Products
    public ArrayList<Product> getAllProducts() {
        return products;
    }

    // 2. Add a Product
    public boolean addProduct(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                return false;
            }
        }
        products.add(product);
        return true;
    }

    // 3. Update a Product
    public boolean updateProduct(int id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    // 4. Delete a Product
    public boolean deleteProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    // 5. Search a Product by ID //
    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }




    // Extra 2
    //==================================
    // Add a rating to a product
    public boolean addRating(int productId, int rating) {
        Product product = getProductById(productId);
        if (product == null || rating < 1 || rating > 5) {
            return false; // Invalid product or rating
        }

        // Ensure ratings list is initialized
        if (product.getRatings() == null) {
            product.setRatings(new ArrayList<>());
        }

        //  Add the rating and recalculate the average
        product.getRatings().add(rating);

        double sum = 0;
        for (int r : product.getRatings()) {
            sum += r;
        }
        product.setAverageRating(sum / product.getRatings().size());

        return true;
    }





    //Extra 4

}







