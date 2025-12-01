package com.bugtracking.controller;

import com.bugtracking.model.User;
import java.util.List;

public class AuthService {
    private FileManager fileManager;

    public AuthService() {
        this.fileManager = new FileManager();
    }

    public User login(String username, String password) {
        List<User> users = fileManager.loadUsers();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null; // Login failed
    }
}
