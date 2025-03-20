package com.example.capstone1.Service;


import com.example.capstone1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    private ArrayList<Category> categories = new ArrayList<>();

    // 1. Get all Categories
    public ArrayList<Category> getAllCategories() {
        return categories;
    }

    // 2. Add a Category
    public boolean addCategory(Category category) {
        for (Category c : categories) {
            if (c.getId() == category.getId()) {
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    // 3. Update a Category
    public boolean updateCategory(int id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == id) {
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    // 4. Delete a Category
    public boolean deleteCategory(int id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == id) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    // 5. Search a Category by ID
    public Category getCategoryById(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

}
