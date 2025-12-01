package com.bugtracking.view;

import com.bugtracking.controller.AuthService;
import com.bugtracking.model.User;
import com.bugtracking.view.admin.AdminDashboard;
import com.bugtracking.view.dev.DevDashboard;
import com.bugtracking.view.pm.PMDashboard;
import com.bugtracking.view.tester.TesterDashboard;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AuthService authService;
    private User currentUser;

    public MainFrame() {
        authService = new AuthService();

        setTitle("Bug Tracking System - University Project");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // CardLayout Setup
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add Login Panel
        mainPanel.add(new LoginPanel(this), "Login");

        add(mainPanel);
    }

    // Method to switch views
    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }

    // Method to handle successful login
    public void onLoginSuccess(User user) {
        this.currentUser = user;

        // Dynamically load the correct dashboard based on Role
        // Inside MainFrame.java -> onLoginSuccess()
        switch (user.getRole()) {
            case "ADMIN":
                mainPanel.add(new AdminDashboard(user, this), "AdminDashboard");
                showCard("AdminDashboard");
                break;
            case "TESTER":
                mainPanel.add(new TesterDashboard(user, this), "TesterDashboard");
                showCard("TesterDashboard");
                break;
            case "DEV":
                mainPanel.add(new DevDashboard(user, this), "DevDashboard");
                showCard("DevDashboard");
                break;
            case "PM":
                mainPanel.add(new PMDashboard(user, this), "PMDashboard");
                showCard("PMDashboard");
                break;
        }
    }

    public void logout() {
        this.currentUser = null;
        showCard("Login");
    }
}
