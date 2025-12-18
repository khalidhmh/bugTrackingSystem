package ui.tester;

import dao.BugDAO;
import dao.UserDAO;
import enums.BugLevel;
import enums.BugPriority;
import enums.BugStatus;
import enums.BugType;
import enums.Role;
import models.Bug;
import models.User;
import services.EmailService;
import services.SessionManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Form for testers to report new bugs with all required fields:
 * - Bug name (title)
 * - Bug type
 * - Priority
 * - Bug level
 * - Project name
 * - Bug date
 * - Status
 * - Assign to developer
 * - Attach screenshot
 */
public class ReportBugForm extends javax.swing.JDialog {

    private BugDAO bugDAO;
    private UserDAO userDAO;
    private User currentUser;
    private String selectedScreenshotPath = null;
    private TesterDashboard parentDashboard;

    // Form components
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel formPanel;
    
    // Form fields
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtTitle;
    private javax.swing.JLabel lblProjectName;
    private javax.swing.JTextField txtProjectName;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JScrollPane descScrollPane;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JLabel lblBugType;
    private javax.swing.JComboBox<BugType> cmbBugType;
    private javax.swing.JLabel lblPriority;
    private javax.swing.JComboBox<BugPriority> cmbPriority;
    private javax.swing.JLabel lblBugLevel;
    private javax.swing.JComboBox<BugLevel> cmbBugLevel;
    private javax.swing.JLabel lblBugDate;
    private javax.swing.JTextField txtBugDate;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JComboBox<BugStatus> cmbStatus;
    private javax.swing.JLabel lblAssignee;
    private javax.swing.JComboBox<String> cmbAssignee;
    private javax.swing.JLabel lblScreenshot;
    private javax.swing.JButton btnBrowseScreenshot;
    private javax.swing.JLabel lblScreenshotPath;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnCancel;

    private boolean bugReported = false;
    
    public ReportBugForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.bugDAO = new BugDAO();
        this.userDAO = new UserDAO();
        this.currentUser = SessionManager.getInstance().getCurrentUser();
        if (parent instanceof TesterDashboard) {
            this.parentDashboard = (TesterDashboard) parent;
        }
        initComponents();
        loadDevelopers();
        setLocationRelativeTo(parent);
    }
    
    public ReportBugForm(java.awt.Frame parent, boolean modal, User tester) {
        super(parent, modal);
        this.bugDAO = new BugDAO();
        this.userDAO = new UserDAO();
        this.currentUser = tester;
        if (parent instanceof TesterDashboard) {
            this.parentDashboard = (TesterDashboard) parent;
        }
        initComponents();
        loadDevelopers();
        setLocationRelativeTo(parent);
    }
    
    public boolean isBugReported() {
        return bugReported;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        headerPanel = new javax.swing.JPanel();
        lblHeader = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        formPanel = new javax.swing.JPanel();
        
        // Initialize form fields
        lblTitle = new javax.swing.JLabel("Bug Title: *");
        txtTitle = new javax.swing.JTextField();
        lblProjectName = new javax.swing.JLabel("Project Name: *");
        txtProjectName = new javax.swing.JTextField();
        lblDescription = new javax.swing.JLabel("Description: *");
        txtDescription = new javax.swing.JTextArea();
        descScrollPane = new javax.swing.JScrollPane(txtDescription);
        lblBugType = new javax.swing.JLabel("Bug Type: *");
        cmbBugType = new javax.swing.JComboBox<>(BugType.values());
        lblPriority = new javax.swing.JLabel("Priority: *");
        cmbPriority = new javax.swing.JComboBox<>(BugPriority.values());
        lblBugLevel = new javax.swing.JLabel("Bug Level: *");
        cmbBugLevel = new javax.swing.JComboBox<>(BugLevel.values());
        lblBugDate = new javax.swing.JLabel("Bug Date: *");
        txtBugDate = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel("Status:");
        cmbStatus = new javax.swing.JComboBox<>(BugStatus.values());
        lblAssignee = new javax.swing.JLabel("Assign to Developer: *");
        cmbAssignee = new javax.swing.JComboBox<>();
        lblScreenshot = new javax.swing.JLabel("Screenshot:");
        btnBrowseScreenshot = new javax.swing.JButton("Browse...");
        lblScreenshotPath = new javax.swing.JLabel("No file selected");
        btnSubmit = new javax.swing.JButton("Submit Bug");
        btnCancel = new javax.swing.JButton("Cancel");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Report New Bug");
        setModal(true);
        setPreferredSize(new Dimension(600, 700));

        // Header Panel
        headerPanel.setBackground(new java.awt.Color(0, 102, 153));
        lblHeader.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(java.awt.Color.WHITE);
        lblHeader.setText("Report New Bug");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblHeader)
                .addGap(15, 15, 15))
        );

        // Setup form fields
        txtDescription.setRows(4);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        
        // Set default date to today
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtBugDate.setText(sdf.format(new Date()));
        
        // Set default status to OPEN
        cmbStatus.setSelectedItem(BugStatus.OPEN);
        cmbStatus.setEnabled(false); // Testers can only create OPEN bugs
        
        // Style buttons
        btnSubmit.setBackground(new Color(0, 153, 76));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSubmit.addActionListener(evt -> submitBug());
        
        btnCancel.setBackground(new Color(153, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.addActionListener(evt -> dispose());
        
        btnBrowseScreenshot.addActionListener(evt -> browseScreenshot());
        
        lblScreenshotPath.setForeground(Color.GRAY);
        lblScreenshotPath.setFont(new Font("Segoe UI", Font.ITALIC, 11));

        // Form Panel Layout
        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProjectName)
                    .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDescription)
                    .addComponent(descScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBugType)
                            .addComponent(cmbBugType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPriority)
                            .addComponent(cmbPriority, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBugLevel)
                            .addComponent(cmbBugLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBugDate)
                            .addComponent(txtBugDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStatus)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblAssignee)
                    .addComponent(cmbAssignee, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblScreenshot)
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addComponent(btnBrowseScreenshot, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblScreenshotPath, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitle)
                .addGap(5, 5, 5)
                .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblProjectName)
                .addGap(5, 5, 5)
                .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblDescription)
                .addGap(5, 5, 5)
                .addComponent(descScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBugType)
                    .addComponent(lblPriority)
                    .addComponent(lblBugLevel))
                .addGap(5, 5, 5)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBugType, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPriority, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBugLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBugDate)
                    .addComponent(lblStatus))
                .addGap(5, 5, 5)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBugDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(lblAssignee)
                .addGap(5, 5, 5)
                .addComponent(cmbAssignee, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblScreenshot)
                .addGap(5, 5, 5)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBrowseScreenshot, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblScreenshotPath))
                .addGap(30, 30, 30)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        scrollPane.setViewportView(formPanel);

        // Main Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrollPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE))
        );

        pack();
    }

    private void loadDevelopers() {
        List<User> developers = userDAO.getUsersByRole(Role.DEVELOPER);
        cmbAssignee.removeAllItems();
        cmbAssignee.addItem("-- Select Developer --");
        for (User dev : developers) {
            cmbAssignee.addItem(dev.getId() + " - " + dev.getUsername() + " (" + dev.getEmail() + ")");
        }
    }

    private void browseScreenshot() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Screenshot");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedScreenshotPath = selectedFile.getAbsolutePath();
            lblScreenshotPath.setText(selectedFile.getName());
            lblScreenshotPath.setForeground(new Color(0, 100, 0));
        }
    }

    private void submitBug() {
        // Validate required fields
        String title = txtTitle.getText().trim();
        String projectName = txtProjectName.getText().trim();
        String description = txtDescription.getText().trim();
        String bugDateStr = txtBugDate.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bug title is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtTitle.requestFocus();
            return;
        }

        if (projectName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Project name is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtProjectName.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Description is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtDescription.requestFocus();
            return;
        }

        if (bugDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bug date is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtBugDate.requestFocus();
            return;
        }

        if (cmbAssignee.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a developer to assign!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            cmbAssignee.requestFocus();
            return;
        }

        // Get assignee ID
        String assigneeSelection = (String) cmbAssignee.getSelectedItem();
        int assigneeId = Integer.parseInt(assigneeSelection.split(" - ")[0]);

        // Handle screenshot copy to project folder
        String savedScreenshotPath = null;
        if (selectedScreenshotPath != null && !selectedScreenshotPath.isEmpty()) {
            try {
                // Create screenshots directory if it doesn't exist
                File screenshotsDir = new File("data/screenshots");
                if (!screenshotsDir.exists()) {
                    screenshotsDir.mkdirs();
                }
                
                // Copy file to screenshots directory
                File sourceFile = new File(selectedScreenshotPath);
                String newFileName = System.currentTimeMillis() + "_" + sourceFile.getName();
                File destFile = new File(screenshotsDir, newFileName);
                Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                savedScreenshotPath = destFile.getPath();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Failed to save screenshot: " + e.getMessage(), 
                    "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        // Create new bug
        Bug newBug = new Bug();
        newBug.setTitle(title);
        newBug.setProjectName(projectName);
        newBug.setDescription(description);
        newBug.setBugType((BugType) cmbBugType.getSelectedItem());
        newBug.setPriority((BugPriority) cmbPriority.getSelectedItem());
        newBug.setBugLevel((BugLevel) cmbBugLevel.getSelectedItem());
        newBug.setStatus(BugStatus.OPEN);
        newBug.setReporterId(currentUser.getId());
        newBug.setAssigneeId(assigneeId);
        newBug.setScreenshotPath(savedScreenshotPath);
        newBug.setBugDate(bugDateStr);

        // Save bug
        boolean success = bugDAO.addBug(newBug);
        
        if (success) {
            // Send email notification to developer
            User developer = userDAO.getUserById(assigneeId);
            if (developer != null && developer.getEmail() != null && !developer.getEmail().isEmpty()) {
                String subject = "New Bug Assigned: " + title;
                String body = String.format(
                    "Hello %s,\n\n" +
                    "A new bug has been assigned to you:\n\n" +
                    "Title: %s\n" +
                    "Project: %s\n" +
                    "Type: %s\n" +
                    "Priority: %s\n" +
                    "Level: %s\n" +
                    "Description: %s\n" +
                    "Reported by: %s\n" +
                    "Date: %s\n\n" +
                    "Please review and address this bug at your earliest convenience.\n\n" +
                    "Best regards,\n" +
                    "Bug Tracking System",
                    developer.getUsername(),
                    title,
                    projectName,
                    cmbBugType.getSelectedItem(),
                    cmbPriority.getSelectedItem(),
                    cmbBugLevel.getSelectedItem(),
                    description,
                    currentUser.getUsername(),
                    bugDateStr
                );
                
                EmailService.sendEmailAsync(developer.getEmail(), subject, body);
            }
            
            JOptionPane.showMessageDialog(this, 
                "Bug reported successfully!\nDeveloper has been notified via email.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            bugReported = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to report bug. Please try again.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
