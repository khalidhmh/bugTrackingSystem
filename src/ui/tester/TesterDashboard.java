package ui.tester;

import dao.BugDAO;
import dao.UserDAO;
import enums.BugStatus;
import models.Bug;
import models.User;
import services.SessionManager;
import ui.common.LoginForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tester Dashboard - Main screen for testers
 * Features:
 * - View dashboard statistics (total bugs, open, in progress, resolved)
 * - Monitor all reported bugs
 * - Report new bugs
 * - Search bugs
 * 
 * @author Team
 */
public class TesterDashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TesterDashboard.class.getName());
    
    private User currentTester;
    private BugDAO bugDAO;
    private UserDAO userDAO;
    
    // GUI Components
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JButton btnLogout;
    private javax.swing.JTabbedPane mainTabbedPane;
    
    // Dashboard Tab
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JPanel totalBugsPanel;
    private javax.swing.JLabel lblTotalBugsTitle;
    private javax.swing.JLabel lblTotalBugsValue;
    private javax.swing.JPanel openBugsPanel;
    private javax.swing.JLabel lblOpenBugsTitle;
    private javax.swing.JLabel lblOpenBugsValue;
    private javax.swing.JPanel inProgressPanel;
    private javax.swing.JLabel lblInProgressTitle;
    private javax.swing.JLabel lblInProgressValue;
    private javax.swing.JPanel resolvedPanel;
    private javax.swing.JLabel lblResolvedTitle;
    private javax.swing.JLabel lblResolvedValue;
    
    // My Bugs Tab
    private javax.swing.JPanel bugsPanel;
    private javax.swing.JPanel bugsTopPanel;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnReportBug;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JScrollPane bugsScrollPane;
    private javax.swing.JTable bugsTable;

    /**
     * Creates new form TesterDashboard
     */
    public TesterDashboard() {
        this.currentTester = SessionManager.getInstance().getCurrentUser();
        this.bugDAO = new BugDAO();
        this.userDAO = new UserDAO();
        initComponents();
        setLocationRelativeTo(null);
        initializeDashboard();
    }
    
    public TesterDashboard(User tester) {
        this.currentTester = tester;
        this.bugDAO = new BugDAO();
        this.userDAO = new UserDAO();
        initComponents();
        setLocationRelativeTo(null);
        initializeDashboard();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Initialize all components
        headerPanel = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        mainTabbedPane = new javax.swing.JTabbedPane();
        
        // Dashboard components
        dashboardPanel = new javax.swing.JPanel();
        statsPanel = new javax.swing.JPanel();
        totalBugsPanel = new javax.swing.JPanel();
        lblTotalBugsTitle = new javax.swing.JLabel();
        lblTotalBugsValue = new javax.swing.JLabel();
        openBugsPanel = new javax.swing.JPanel();
        lblOpenBugsTitle = new javax.swing.JLabel();
        lblOpenBugsValue = new javax.swing.JLabel();
        inProgressPanel = new javax.swing.JPanel();
        lblInProgressTitle = new javax.swing.JLabel();
        lblInProgressValue = new javax.swing.JLabel();
        resolvedPanel = new javax.swing.JPanel();
        lblResolvedTitle = new javax.swing.JLabel();
        lblResolvedValue = new javax.swing.JLabel();
        
        // My Bugs components
        bugsPanel = new javax.swing.JPanel();
        bugsTopPanel = new javax.swing.JPanel();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnReportBug = new javax.swing.JButton();
        btnViewDetails = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        bugsScrollPane = new javax.swing.JScrollPane();
        bugsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tester Dashboard - Bug Tracking System");
        setPreferredSize(new Dimension(1100, 700));

        // ==================== HEADER PANEL ====================
        headerPanel.setBackground(new Color(0, 102, 102));
        headerPanel.setPreferredSize(new Dimension(1100, 60));

        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setText("Bug Tracking System - Tester Panel");

        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setText("Welcome, Tester");

        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setText("Logout");
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblWelcome)
                .addGap(18, 18, 18)
                .addComponent(btnLogout)
                .addGap(20, 20, 20))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(lblWelcome)
                    .addComponent(btnLogout))
                .addGap(15, 15, 15))
        );

        // ==================== TABBED PANE ====================
        mainTabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // ==================== DASHBOARD TAB ====================
        dashboardPanel.setBackground(new Color(245, 245, 245));
        
        // Stats Panel with GridLayout
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new GridLayout(1, 4, 20, 0));

        // Total Bugs Panel
        totalBugsPanel.setBackground(new Color(0, 123, 255));
        lblTotalBugsTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTotalBugsTitle.setForeground(Color.WHITE);
        lblTotalBugsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalBugsTitle.setText("My Total Bugs");
        lblTotalBugsValue.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblTotalBugsValue.setForeground(Color.WHITE);
        lblTotalBugsValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalBugsValue.setText("0");
        setupStatPanel(totalBugsPanel, lblTotalBugsTitle, lblTotalBugsValue);
        statsPanel.add(totalBugsPanel);

        // Open Bugs Panel
        openBugsPanel.setBackground(new Color(220, 53, 69));
        lblOpenBugsTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblOpenBugsTitle.setForeground(Color.WHITE);
        lblOpenBugsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblOpenBugsTitle.setText("Open Bugs");
        lblOpenBugsValue.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblOpenBugsValue.setForeground(Color.WHITE);
        lblOpenBugsValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblOpenBugsValue.setText("0");
        setupStatPanel(openBugsPanel, lblOpenBugsTitle, lblOpenBugsValue);
        statsPanel.add(openBugsPanel);

        // In Progress Panel
        inProgressPanel.setBackground(new Color(255, 193, 7));
        lblInProgressTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblInProgressTitle.setForeground(Color.WHITE);
        lblInProgressTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblInProgressTitle.setText("In Progress");
        lblInProgressValue.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblInProgressValue.setForeground(Color.WHITE);
        lblInProgressValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblInProgressValue.setText("0");
        setupStatPanel(inProgressPanel, lblInProgressTitle, lblInProgressValue);
        statsPanel.add(inProgressPanel);

        // Resolved Panel
        resolvedPanel.setBackground(new Color(40, 167, 69));
        lblResolvedTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblResolvedTitle.setForeground(Color.WHITE);
        lblResolvedTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblResolvedTitle.setText("Resolved/Closed");
        lblResolvedValue.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblResolvedValue.setForeground(Color.WHITE);
        lblResolvedValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblResolvedValue.setText("0");
        setupStatPanel(resolvedPanel, lblResolvedTitle, lblResolvedValue);
        statsPanel.add(resolvedPanel);

        // Dashboard layout
        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(statsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(statsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab("Dashboard", dashboardPanel);

        // ==================== MY BUGS TAB ====================
        bugsPanel.setBackground(new Color(245, 245, 245));
        bugsTopPanel.setOpaque(false);

        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSearch.setText("Search:");

        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(200, 30));
        txtSearch.addActionListener(e -> btnSearchActionPerformed(null));

        btnSearch.setText("Search");
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        btnReportBug.setBackground(new Color(40, 167, 69));
        btnReportBug.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnReportBug.setForeground(Color.WHITE);
        btnReportBug.setText("+ Report New Bug");
        btnReportBug.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReportBug.addActionListener(this::btnReportBugActionPerformed);

        btnViewDetails.setBackground(new Color(0, 123, 255));
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setText("View Details");
        btnViewDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewDetails.addActionListener(this::btnViewDetailsActionPerformed);

        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        // Bugs Table
        bugsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        bugsTable.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Title", "Project", "Type", "Priority", "Level", "Status", "Assigned To", "Date"}
        ) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        bugsTable.setRowHeight(30);
        bugsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bugsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        bugsScrollPane.setViewportView(bugsTable);
        
        // Double-click to view details
        bugsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    btnViewDetailsActionPerformed(null);
                }
            }
        });

        // Bugs Top Panel layout
        javax.swing.GroupLayout bugsTopPanelLayout = new javax.swing.GroupLayout(bugsTopPanel);
        bugsTopPanel.setLayout(bugsTopPanelLayout);
        bugsTopPanelLayout.setHorizontalGroup(
            bugsTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugsTopPanelLayout.createSequentialGroup()
                .addComponent(lblSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReportBug)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnViewDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh))
        );
        bugsTopPanelLayout.setVerticalGroup(
            bugsTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugsTopPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(bugsTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnReportBug)
                    .addComponent(btnViewDetails)
                    .addComponent(btnRefresh))
                .addGap(10, 10, 10))
        );

        // Bugs Panel layout
        javax.swing.GroupLayout bugsPanelLayout = new javax.swing.GroupLayout(bugsPanel);
        bugsPanel.setLayout(bugsPanelLayout);
        bugsPanelLayout.setHorizontalGroup(
            bugsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugsPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(bugsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bugsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
                    .addComponent(bugsTopPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        bugsPanelLayout.setVerticalGroup(
            bugsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugsPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(bugsTopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bugsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        mainTabbedPane.addTab("My Bugs", bugsPanel);

        // ==================== MAIN LAYOUT ====================
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
            .addComponent(mainTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainTabbedPane))
        );

        pack();
    }
    
    private void setupStatPanel(JPanel panel, JLabel titleLabel, JLabel valueLabel) {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(valueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valueLabel)
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }

    // ==================== EVENT HANDLERS ====================

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.getInstance().logout();
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String searchText = txtSearch.getText().trim();
        
        if (searchText.isEmpty()) {
            refreshBugTable();
            return;
        }
        
        List<Bug> myBugs = bugDAO.findByReporterId(currentTester.getId());
        List<Bug> results = new java.util.ArrayList<>();
        
        for (Bug bug : myBugs) {
            if (bug.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                bug.getDescription().toLowerCase().contains(searchText.toLowerCase()) ||
                (bug.getProjectName() != null && bug.getProjectName().toLowerCase().contains(searchText.toLowerCase()))) {
                results.add(bug);
            }
        }
        
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No bugs found matching: " + searchText,
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        updateBugsTable(results);
    }

    private void btnReportBugActionPerformed(java.awt.event.ActionEvent evt) {
        ReportBugForm reportForm = new ReportBugForm(this, true, currentTester);
        reportForm.setVisible(true);
        
        if (reportForm.isBugReported()) {
            refreshBugTable();
            updateStatistics();
        }
    }

    private void btnViewDetailsActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = bugsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a bug to view details.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bugId = (Integer) bugsTable.getValueAt(selectedRow, 0);
        Bug selectedBug = bugDAO.findById(bugId);
        
        if (selectedBug != null) {
            showBugDetails(selectedBug);
        }
    }
    
    private void showBugDetails(Bug bug) {
        User assignee = userDAO.findById(bug.getAssigneeId());
        String assigneeName = assignee != null ? assignee.getFullName() : "Unassigned";
        
        String details = String.format(
            "Bug #%d\n\n" +
            "Title: %s\n" +
            "Project: %s\n" +
            "Type: %s\n" +
            "Priority: %s\n" +
            "Level: %s\n" +
            "Status: %s\n" +
            "Assigned To: %s\n" +
            "Bug Date: %s\n" +
            "Created: %s\n\n" +
            "Description:\n%s\n\n" +
            "Screenshot: %s",
            bug.getId(),
            bug.getTitle(),
            bug.getProjectName() != null ? bug.getProjectName() : "N/A",
            bug.getBugType() != null ? bug.getBugType() : "N/A",
            bug.getPriority() != null ? bug.getPriority() : "N/A",
            bug.getBugLevel() != null ? bug.getBugLevel() : "N/A",
            bug.getStatus(),
            assigneeName,
            bug.getBugDate() != null ? bug.getBugDate().toString() : "N/A",
            bug.getCreatedAt() != null ? bug.getCreatedAt().toLocalDate().toString() : "N/A",
            bug.getDescription(),
            bug.hasScreenshot() ? bug.getScreenshotPath() : "No screenshot attached"
        );
        
        JTextArea textArea = new JTextArea(details);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Bug Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        txtSearch.setText("");
        refreshBugTable();
        updateStatistics();
        JOptionPane.showMessageDialog(this, "Data refreshed successfully!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
    }

    // ==================== HELPER METHODS ====================

    private void initializeDashboard() {
        if (currentTester != null) {
            lblWelcome.setText("Welcome, " + currentTester.getFullName());
        }
        updateStatistics();
        refreshBugTable();
    }
    
    private void updateStatistics() {
        if (currentTester == null) return;
        
        bugDAO.refresh();
        List<Bug> myBugs = bugDAO.findByReporterId(currentTester.getId());
        
        int totalBugs = myBugs.size();
        int openBugs = countBugsByStatus(myBugs, BugStatus.OPEN);
        int inProgressBugs = countBugsByStatus(myBugs, BugStatus.IN_PROGRESS);
        int resolvedBugs = countBugsByStatus(myBugs, BugStatus.RESOLVED) + 
                          countBugsByStatus(myBugs, BugStatus.CLOSED);
        
        lblTotalBugsValue.setText(String.valueOf(totalBugs));
        lblOpenBugsValue.setText(String.valueOf(openBugs));
        lblInProgressValue.setText(String.valueOf(inProgressBugs));
        lblResolvedValue.setText(String.valueOf(resolvedBugs));
    }
    
    private int countBugsByStatus(List<Bug> bugs, BugStatus status) {
        int count = 0;
        for (Bug bug : bugs) {
            if (bug.getStatus() == status) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Refresh the bug table with current user's bugs
     */
    public void refreshBugTable() {
        if (currentTester == null) return;
        
        bugDAO.refresh();
        List<Bug> myBugs = bugDAO.findByReporterId(currentTester.getId());
        updateBugsTable(myBugs);
    }
    
    private void updateBugsTable(List<Bug> bugs) {
        DefaultTableModel model = (DefaultTableModel) bugsTable.getModel();
        model.setRowCount(0);
        
        for (Bug bug : bugs) {
            User assignedDev = userDAO.findById(bug.getAssigneeId());
            String devName = assignedDev != null ? assignedDev.getFullName() : "Unassigned";
            
            model.addRow(new Object[]{
                bug.getId(),
                bug.getTitle(),
                bug.getProjectName() != null ? bug.getProjectName() : "N/A",
                bug.getBugType() != null ? bug.getBugType() : "N/A",
                bug.getPriority() != null ? bug.getPriority() : "N/A",
                bug.getBugLevel() != null ? bug.getBugLevel() : "N/A",
                bug.getStatus(),
                devName,
                bug.getBugDate() != null ? bug.getBugDate().toString() : "N/A"
            });
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new TesterDashboard().setVisible(true));
    }
}
