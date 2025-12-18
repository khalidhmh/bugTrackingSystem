package ui.developer;

import models.Bug;
import enums.BugStatus;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog for viewing and updating bug details
 * Shows read-only fields (ID, Title) and editable fields (Description, Status)
 */
public class BugDetailsDialog extends JDialog {
    
    private Bug bug;
    private JTextField txtId;
    private JTextField txtTitle;
    private JTextArea txtDescription;
    private JComboBox<BugStatus> cmbStatus;
    private JButton btnSave;
    private JButton btnCancel;
    
    /**
     * Constructor
     * @param parent The parent frame
     * @param bug The bug to display/edit
     */
    public BugDetailsDialog(JFrame parent, Bug bug) {
        super(parent, "Bug Details - ID: " + bug.getId(), true);
        this.bug = bug;
        
        initComponents();
        loadBugData();
        
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Main panel with form fields
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Bug ID (Read-only)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblId = new JLabel("Bug ID:");
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainPanel.add(lblId, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtId = new JTextField();
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtId.setEditable(false);
        txtId.setBackground(new Color(240, 240, 240));
        mainPanel.add(txtId, gbc);
        
        // Bug Title (Read-only)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblTitle = new JLabel("Title:");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainPanel.add(lblTitle, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtTitle = new JTextField();
        txtTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTitle.setEditable(false);
        txtTitle.setBackground(new Color(240, 240, 240));
        mainPanel.add(txtTitle, gbc);
        
        // Description (Editable)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainPanel.add(lblDescription, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescription = new JTextArea(8, 30);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollDescription = new JScrollPane(txtDescription);
        mainPanel.add(scrollDescription, gbc);
        
        // Status (Editable dropdown)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainPanel.add(lblStatus, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cmbStatus = new JComboBox<>(BugStatus.values());
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbStatus.setPreferredSize(new Dimension(200, 30));
        mainPanel.add(cmbStatus, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        btnSave = new JButton("Save Changes");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setPreferredSize(new Dimension(130, 35));
        btnSave.addActionListener(e -> saveChanges());
        
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnCancel.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Loads bug data into form fields
     */
    private void loadBugData() {
        txtId.setText(String.valueOf(bug.getId()));
        txtTitle.setText(bug.getTitle());
        txtDescription.setText(bug.getDescription());
        cmbStatus.setSelectedItem(bug.getStatus());
    }
    
    /**
     * Saves changes to the bug
     */
    private void saveChanges() {
        try {
            // Update bug with new values
            String newDescription = txtDescription.getText().trim();
            BugStatus newStatus = (BugStatus) cmbStatus.getSelectedItem();
            
            // Validate description
            if (newDescription.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Description cannot be empty!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update bug object
            bug.setDescription(newDescription);
            bug.setStatus(newStatus);
            
            // Show success message
            JOptionPane.showMessageDialog(this,
                "Bug updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // TODO: Here you would save to database/file
            // Example: bugDAO.updateBug(bug);
            
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error updating bug: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Gets the updated bug object
     * @return The bug with updated values
     */
    public Bug getUpdatedBug() {
        return this.bug;
    }
}
