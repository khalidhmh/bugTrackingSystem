package ui.tester;

import enums.BugSeverityEnum;
import enums.BugStatusEnum;
import dao.BugDAO;
import dao.UserDAO;
import models.Bug;
import models.Role;
import models.User;
import java.io.File;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Report Bug Form - Form to create new bug
 * @author Team
 */
public class ReportBugForm extends javax.swing.JDialog {

    private File selectedPocFile;
    private boolean bugReported = false;
    private int currentTesterId;

    public ReportBugForm(java.awt.Frame parent, boolean modal, int testerId) {
        super(parent, modal);
        this.currentTesterId = testerId;
        initComponents();
        setLocationRelativeTo(parent);
        loadDevelopers();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bugTitleLabel = new javax.swing.JLabel();
        bugDescriptionLabel = new javax.swing.JLabel();
        bugTitleText = new javax.swing.JTextField();
        bugDescriptionScrollPane = new javax.swing.JScrollPane();
        bugDescriptionText = new javax.swing.JTextArea();
        assignBugBtn = new javax.swing.JButton();
        severityList = new javax.swing.JComboBox<>();
        severityLabel = new javax.swing.JLabel();
        developerLabel = new javax.swing.JLabel();
        devList = new javax.swing.JComboBox<>();
        PoCLabel = new javax.swing.JLabel();
        addPoCBtn = new javax.swing.JButton();
        pocFileLabel = new javax.swing.JLabel();

        setTitle("Report New Bug");
        setMinimumSize(new java.awt.Dimension(700, 500));

        bugTitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bugTitleLabel.setText("Title:");

        bugDescriptionLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bugDescriptionLabel.setText("Description:");

        bugTitleText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        bugDescriptionText.setColumns(20);
        bugDescriptionText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bugDescriptionText.setRows(5);
        bugDescriptionText.setLineWrap(true);
        bugDescriptionText.setWrapStyleWord(true);
        bugDescriptionScrollPane.setViewportView(bugDescriptionText);

        assignBugBtn.setBackground(new java.awt.Color(40, 167, 69));
        assignBugBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        assignBugBtn.setForeground(new java.awt.Color(255, 255, 255));
        assignBugBtn.setText("Report & Assign Bug");
        assignBugBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        assignBugBtn.addActionListener(this::assignBugBtnActionPerformed);

        severityList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        severityList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CRITICAL", "HIGH", "MEDIUM", "LOW", "INFO" }));

        severityLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        severityLabel.setText("Severity:");

        developerLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        developerLabel.setText("Assign to Developer:");

        devList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        PoCLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PoCLabel.setText("Proof of Concept:");

        addPoCBtn.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        addPoCBtn.setText("Choose File...");
        addPoCBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addPoCBtn.addActionListener(this::addPoCBtnActionPerformed);

        pocFileLabel.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        pocFileLabel.setForeground(new java.awt.Color(102, 102, 102));
        pocFileLabel.setText("No file selected");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bugTitleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bugTitleText))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bugDescriptionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bugDescriptionScrollPane))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(severityLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(severityList, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(developerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(devList, 0, 195, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PoCLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addPoCBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pocFileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(assignBugBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bugTitleLabel)
                    .addComponent(bugTitleText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bugDescriptionLabel)
                    .addComponent(bugDescriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(severityLabel)
                    .addComponent(severityList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(developerLabel)
                    .addComponent(devList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PoCLabel)
                    .addComponent(addPoCBtn)
                    .addComponent(pocFileLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(assignBugBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>

    private void assignBugBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignBugBtnActionPerformed
        // Validate inputs
        String title = bugTitleText.getText().trim();
        String description = bugDescriptionText.getText().trim();
        
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a bug title.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            bugTitleText.requestFocus();
            return;
        }
        
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a bug description.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            bugDescriptionText.requestFocus();
            return;
        }
        
        if (devList.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a developer to assign the bug.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Create new bug
            Bug bug = new Bug();
            bug.setTitle(title);
            bug.setDescription(description);
            
            // Set severity
            String severityStr = (String) severityList.getSelectedItem();
            bug.setSeverity(BugSeverityEnum.valueOf(severityStr));
            
            // Set status
            bug.setStatus(BugStatusEnum.OPEN);
            
            // Set reporter (current tester)
            bug.setReporterId(currentTesterId);
            
            // Set assigned developer
            User selectedDeveloper = (User) devList.getSelectedItem();
            bug.setAssignedDeveloperId(selectedDeveloper.getId());
            
            // Set PoC file path if selected
            if (selectedPocFile != null) {
                bug.setPocFilePath(selectedPocFile.getAbsolutePath());
            }
            
            // Save bug to database
            BugDAO bugDAO = new BugDAO();
            Bug savedBug = bugDAO.save(bug);
            
            JOptionPane.showMessageDialog(this, 
                "Bug #" + savedBug.getId() + " reported successfully!\n" +
                "Title: " + savedBug.getTitle() + "\n" +
                "Assigned to: " + selectedDeveloper.getFullName() + "\n" +
                "Severity: " + savedBug.getSeverity().getDisplayName(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            bugReported = true;
            clearForm();
            this.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error reporting bug: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_assignBugBtnActionPerformed

    private void addPoCBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPoCBtnActionPerformed
        openFileChooser();
    }//GEN-LAST:event_addPoCBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PoCLabel;
    private javax.swing.JButton addPoCBtn;
    private javax.swing.JButton assignBugBtn;
    private javax.swing.JLabel bugDescriptionLabel;
    private javax.swing.JScrollPane bugDescriptionScrollPane;
    private javax.swing.JTextArea bugDescriptionText;
    private javax.swing.JLabel bugTitleLabel;
    private javax.swing.JTextField bugTitleText;
    private javax.swing.JComboBox<User> devList;
    private javax.swing.JLabel developerLabel;
    private javax.swing.JLabel pocFileLabel;
    private javax.swing.JLabel severityLabel;
    private javax.swing.JComboBox<String> severityList;
    // End of variables declaration//GEN-END:variables

    private void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Proof of Concept File");
        
        int userSelection = fileChooser.showOpenDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            selectedPocFile = fileChooser.getSelectedFile();
            pocFileLabel.setText(selectedPocFile.getName());
            pocFileLabel.setToolTipText(selectedPocFile.getAbsolutePath());
        }
    }
    
    private void loadDevelopers() {
        UserDAO userDAO = new UserDAO();
        List<User> developers = userDAO.findByRole(Role.DEVELOPER);
        
        DefaultComboBoxModel<User> model = new DefaultComboBoxModel<>();
        
        for (User dev : developers) {
            model.addElement(dev);
        }
        
        devList.setModel(model);
        
        // Set custom renderer to show developer names
        devList.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, 
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof User) {
                    User user = (User) value;
                    setText(user.getFullName() + " (" + user.getUsername() + ")");
                }
                return this;
            }
        });
        
        if (developers.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No developers found in the system.\nPlease contact admin to add developers.", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
        } else {
            devList.setSelectedIndex(0);
        }
    }
    
    private void clearForm() {
        bugTitleText.setText("");
        bugDescriptionText.setText("");
        severityList.setSelectedIndex(0);
        selectedPocFile = null;
        pocFileLabel.setText("No file selected");
        if (devList.getModel().getSize() > 0) {
            devList.setSelectedIndex(0);
        }
    }
    
    public boolean isBugReported() {
        return bugReported;
    }
}
