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

    // 5. Search a Product by ID // also extra 1
    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    //Extra 2
    //===========================================
    // 1. Get products by category
    public ArrayList<Product> getProductsByCategory(int categoryId) {
        ArrayList<Product> categoryProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategoryId() == categoryId) {
                categoryProducts.add(product);
            }
        }
        return categoryProducts;
    }


    // Extra 3
    //==================================
    // Add ratings to the product
    public boolean addRatingsToProduct(Product product, ArrayList<Integer> ratings) {
        // Check if the product is null, return false if the product is not found
        if (product == null) {
            return false; // Product not found
        }

        // Initialize the ratings list if it is null
        if (product.getRatings() == null) {
            product.setRatings(new ArrayList<>()); // Initialize the ratings list if it's null
        }

        // Add new ratings to the product's ratings list
        for (int rating : ratings) {
            product.getRatings().add(rating);
        }

        // Recalculate the average rating after adding new ratings
        double averageRating = calculateAverageRating(product);
        product.setAverageRating(averageRating);
        return true;
    }

    // Calculate the average rating of a product
    public double calculateAverageRating(Product product) {
        // Check if the product is null
        if (product == null) {
            return 0.0; // Return 0.0 if product is not found
        }

        // If ratings is null, initialize it
        if (product.getRatings() == null) {
            product.setRatings(new ArrayList<>());
        }

        // Calculate the sum of ratings if the list is not empty
        double sum = 0;
        for (int rating : product.getRatings()) {
            sum += rating;
        }

        // Return the average rating
        return sum / product.getRatings().size();
    }

    //Extra 4
    // Add product to the list
    // Toggle favorite status of a product
    public boolean toggleFavorite(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                product.setFavorite(!product.isFavorite());  // Toggle favorite status
                return true;
            }
        }
        return false;
    }

    // Get all favorite products
    public ArrayList<Product> getFavoriteProducts() {
        ArrayList<Product> favoriteProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.isFavorite()) {
                favoriteProducts.add(product);
            }
        }
        return favoriteProducts;
    }


}

//        Product product = findProductById(productId);
//        double averageRating = calculateAverageRating(productId);

