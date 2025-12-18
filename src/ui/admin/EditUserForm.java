/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package ui.admin;

import dao.UserDAO;
import enums.Role;
import models.User;
import javax.swing.JOptionPane;

/**
 * Edit User Form - Form to modify existing user
 * @author Team
 */
public class EditUserForm extends javax.swing.JDialog {

    private UserDAO userDAO;
    private User currentUser;
    private boolean userUpdated = false;

    /**
     * Creates new form EditUserForm
     */
    public EditUserForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.userDAO = new UserDAO();
        initComponents();
        setLocationRelativeTo(parent);
        initRoleComboBox();
    }
    
    /**
     * Creates new form EditUserForm with user to edit
     */
    public EditUserForm(java.awt.Frame parent, boolean modal, User user) {
        this(parent, modal);
        this.currentUser = user;
        loadUserData();
    }
    
    /**
     * Creates new form EditUserForm with user ID to edit
     */
    public EditUserForm(java.awt.Frame parent, boolean modal, int userId) {
        this(parent, modal);
        this.currentUser = userDAO.findById(userId);
        if (this.currentUser != null) {
            loadUserData();
        } else {
            showError("User not found with ID: " + userId);
            dispose();
        }
    }
    
    /**
     * Initialize role combo box with available roles
     */
    private void initRoleComboBox() {
        cmbRole.removeAllItems();
        for (Role role : Role.values()) {
            cmbRole.addItem(role.name());
        }
    }
    
    /**
     * Load user data into form fields
     */
    private void loadUserData() {
        if (currentUser == null) return;
        
        lblUserId.setText("User ID: " + currentUser.getId());
        txtFirstName.setText(currentUser.getFirstName());
        txtLastName.setText(currentUser.getLastName());
        txtUsername.setText(currentUser.getUsername());
        txtEmail.setText(currentUser.getEmail());
        cmbRole.setSelectedItem(currentUser.getRole().name());
        
        // Store original username/email for validation
        txtUsername.putClientProperty("originalUsername", currentUser.getUsername());
        txtEmail.putClientProperty("originalEmail", currentUser.getEmail());
    }
    
    /**
     * Set user to edit
     */
    public void setUser(User user) {
        this.currentUser = user;
        loadUserData();
    }
    
    /**
     * Validate form fields
     */
    private boolean validateForm() {
        // First Name validation
        if (txtFirstName.getText().trim().isEmpty()) {
            showError("First name is required");
            txtFirstName.requestFocus();
            return false;
        }
        
        // Last Name validation
        if (txtLastName.getText().trim().isEmpty()) {
            showError("Last name is required");
            txtLastName.requestFocus();
            return false;
        }
        
        // Username validation
        String username = txtUsername.getText().trim();
        String originalUsername = (String) txtUsername.getClientProperty("originalUsername");
        
        if (username.isEmpty()) {
            showError("Username is required");
            txtUsername.requestFocus();
            return false;
        }
        if (username.length() < 3) {
            showError("Username must be at least 3 characters");
            txtUsername.requestFocus();
            return false;
        }
        // Check uniqueness only if username changed
        if (!username.equalsIgnoreCase(originalUsername) && userDAO.existsByUsername(username)) {
            showError("Username '" + username + "' already exists");
            txtUsername.requestFocus();
            return false;
        }
        
        // Email validation
        String email = txtEmail.getText().trim();
        String originalEmail = (String) txtEmail.getClientProperty("originalEmail");
        
        if (email.isEmpty()) {
            showError("Email is required");
            txtEmail.requestFocus();
            return false;
        }
        if (!isValidEmail(email)) {
            showError("Please enter a valid email address");
            txtEmail.requestFocus();
            return false;
        }
        // Check uniqueness only if email changed
        if (!email.equalsIgnoreCase(originalEmail) && userDAO.existsByEmail(email)) {
            showError("Email '" + email + "' already exists");
            txtEmail.requestFocus();
            return false;
        }
        
        // Password validation (only if changing password)
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        if (!password.isEmpty()) {
            if (password.length() < 4) {
                showError("Password must be at least 4 characters");
                txtPassword.requestFocus();
                return false;
            }
            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match");
                txtConfirmPassword.requestFocus();
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Simple email validation
     */
    private boolean isValidEmail(String email) {
        return email != null 
            && email.contains("@") 
            && email.contains(".")
            && email.indexOf("@") < email.lastIndexOf(".");
    }
    
    /**
     * Show error message
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Show success message
     */
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Reset form to original user data
     */
    private void resetForm() {
        loadUserData();
        txtPassword.setText("");
        txtConfirmPassword.setText("");
    }
    
    /**
     * Check if user was updated
     */
    public boolean isUserUpdated() {
        return userUpdated;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblUserId = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblConfirmPassword = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        lblRole = new javax.swing.JLabel();
        cmbRole = new javax.swing.JComboBox<>();
        lblPasswordHint = new javax.swing.JLabel();
        btnPanel = new javax.swing.JPanel();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit User");
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 20, 30));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblTitle.setForeground(new java.awt.Color(51, 51, 51));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Edit User");

        lblUserId.setFont(new java.awt.Font("Segoe UI", 2, 12));
        lblUserId.setForeground(new java.awt.Color(108, 117, 125));
        lblUserId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserId.setText("User ID: ");

        lblFirstName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblFirstName.setText("First Name *");

        txtFirstName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtFirstName.setPreferredSize(new java.awt.Dimension(250, 35));

        lblLastName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblLastName.setText("Last Name *");

        txtLastName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtLastName.setPreferredSize(new java.awt.Dimension(250, 35));

        lblUsername.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblUsername.setText("Username *");

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtUsername.setPreferredSize(new java.awt.Dimension(250, 35));

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblEmail.setText("Email *");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtEmail.setPreferredSize(new java.awt.Dimension(250, 35));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblPassword.setText("New Password");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtPassword.setPreferredSize(new java.awt.Dimension(250, 35));

        lblConfirmPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblConfirmPassword.setText("Confirm Password");

        txtConfirmPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtConfirmPassword.setPreferredSize(new java.awt.Dimension(250, 35));

        lblRole.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblRole.setText("Role *");

        cmbRole.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbRole.setPreferredSize(new java.awt.Dimension(250, 35));

        lblPasswordHint.setFont(new java.awt.Font("Segoe UI", 2, 11));
        lblPasswordHint.setForeground(new java.awt.Color(108, 117, 125));
        lblPasswordHint.setText("(Leave blank to keep current password)");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblUserId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFirstName)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRole)
                    .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPasswordHint))
                .addGap(40, 40, 40)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLastName)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfirmPassword)
                    .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserId)
                .addGap(20, 20, 20)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(lblLastName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(lblConfirmPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPasswordHint)
                .addGap(12, 12, 12)
                .addComponent(lblRole)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btnPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnUpdate.setBackground(new java.awt.Color(0, 123, 255));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update User");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.setPreferredSize(new java.awt.Dimension(130, 40));
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnReset.setBackground(new java.awt.Color(108, 117, 125));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.setPreferredSize(new java.awt.Dimension(100, 40));
        btnReset.addActionListener(this::btnResetActionPerformed);

        btnCancel.setBackground(new java.awt.Color(220, 53, 69));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setPreferredSize(new java.awt.Dimension(100, 40));
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        javax.swing.GroupLayout btnPanelLayout = new javax.swing.GroupLayout(btnPanel);
        btnPanel.setLayout(btnPanelLayout);
        btnPanelLayout.setHorizontalGroup(
            btnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPanelLayout.setVerticalGroup(
            btnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (currentUser == null) {
            showError("No user to update");
            return;
        }
        
        if (!validateForm()) {
            return;
        }
        
        try {
            // Update user data
            currentUser.setFirstName(txtFirstName.getText().trim());
            currentUser.setLastName(txtLastName.getText().trim());
            currentUser.setUsername(txtUsername.getText().trim());
            currentUser.setEmail(txtEmail.getText().trim());
            currentUser.setRole(Role.valueOf((String) cmbRole.getSelectedItem()));
            
            // Update password only if new password provided
            String newPassword = new String(txtPassword.getPassword());
            if (!newPassword.isEmpty()) {
                currentUser.setPassword(newPassword);
            }
            
            // Save to database
            userDAO.update(currentUser);
            userUpdated = true;
            
            showSuccess("User with Username : '" + currentUser.getUsername() + "' updated successfully!");
            dispose();
            
        } catch (Exception ex) {
            showError("Error updating user: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JPanel btnPanel;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbRole;
    private javax.swing.JLabel lblConfirmPassword;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPasswordHint;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUserId;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
