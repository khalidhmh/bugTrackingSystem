package com.bugtracking.view;

import com.bugtracking.controller.AuthService;
import com.bugtracking.model.User;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private AuthService authService;

    public LoginPanel(MainFrame frame) {
        this.mainFrame = frame;
        this.authService = new AuthService();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("Bug Tracking System");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));

        txtUser = new JTextField(15);
        txtPass = new JPasswordField(15);
        JButton btnLogin = new JButton("Login");

        // Toggle Password Visibility (The Eye Icon logic)
        JCheckBox showPass = new JCheckBox("Show Password");
        showPass.addActionListener(e -> {
            if (showPass.isSelected()) txtPass.setEchoChar((char) 0);
            else txtPass.setEchoChar('•');
        });

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            User loggedInUser = authService.login(user, pass);

            if (loggedInUser != null) {
                mainFrame.onLoginSuccess(loggedInUser);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adding components
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; add(lblTitle, gbc);
        gbc.gridwidth = 1; gbc.gridy = 1; add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; add(txtUser, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; add(txtPass, gbc);
        gbc.gridx = 1; gbc.gridy = 3; add(showPass, gbc);
        gbc.gridx = 1; gbc.gridy = 4; add(btnLogin, gbc);
    }
}
