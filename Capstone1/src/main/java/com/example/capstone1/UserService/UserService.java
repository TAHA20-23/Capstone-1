package com.example.capstone1.UserService;

import com.example.capstone1.UserModel.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {


    private ArrayList<User> users = new ArrayList<>();

    // 1. Get all Users
    public ArrayList<User> getAllUsers() {
        return users;
    }

    // 2. Add a User
    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getId() == user.getId()) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    // 3. Update a User
    public boolean updateUser(int id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    // 4. Delete a User
    public boolean deleteUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    // 5. Search a User by ID
    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    //----------------------------------------------------------------

    // 6. Deduct Balance from User
     private boolean deductBalance(int  userId, double amount){
        User user = getUserById(userId);
        if (user != null && user.getBalance() >= amount){
            user.setBalance(user.getBalance()-amount);
            return true;
        }
        return false;
    }

    //Extra 5
    //=========================================================

    // 6. Update the user's name
    public boolean updateUsername(int id, String username) {
        User user = getUserById(id);
        if (user != null) {
            user.setUsername(username);  // Update username
            return true;
        }
        return false;
    }

    // 7. Change the user's password
    public boolean changePassword(int id, String oldPassword, String newPassword) {
        User user = getUserById(id);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }
}
