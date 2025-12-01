package com.bugtracking.model;


import java.io.Serializable;

// Abstract Class (OOP Requirement)
public abstract class User implements Serializable {
    protected String id;
    protected String username;
    protected String password;
    protected String email;
    protected String role; // "ADMIN", "TESTER", "DEV", "PM"

    public User(String id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Abstract method to force subclasses to implement specific menu logic if needed
    public abstract String getDashboardName();

    // Getters and Setters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        // Format for text file: ID;Username;Password;Email;Role
        return id + ";" + username + ";" + password + ";" + email + ";" + role;
    }
}
