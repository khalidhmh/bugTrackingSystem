package com.bugtracking.model;

public class Admin extends User {
    public Admin(String id, String username, String password, String email) {
        super(id, username, password, email, "ADMIN");
    }

    @Override
    public String getDashboardName() {
        return "AdminDashboard";
    }
}
