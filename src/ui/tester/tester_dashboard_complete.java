package ui.tester;

import dao.BugDAO;
import dao.UserDAO;
import models.Bug;
import models.Role;
import models.User;
import enums.BugStatusEnum;
import enums.BugSeverityEnum;
import ui.common.LoginForm;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * Tester Dashboard - Main screen for testers
 * @author Team
 */
public class TesterDashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TesterDashboard.class.getName());
    private User currentTester;

    public TesterDashboard() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    public TesterDashboard(User tester) {
        this.currentTester = tester;
        initComponents();
        setLocationRelativeTo(null);
        initializeDashboard();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        mainTabbedPane = new javax.swing.JTabbedPane();
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
        bugsTablePanel = new javax.swing.JPanel();
        bugsTopPanel = new javax.swing.JPanel();
        lblMyBugs = new javax.swing.JLabel();
        btnReportBug = new javax.swing.JButton();
        btnRefreshBugs = new javax.swing.JButton();
        filterLabel = new javax.swing.JLabel();
        filterComboBox = new javax.swing.JComboBox<>();
        bugsScrollPane = new javax.swing.JScrollPane();
        bugsTable = new javax.swing.JTable();
        reportBugPanel = new javax.swing.JPanel();
        reportBugLabel = new javax.swing.JLabel();
        reportBugInstructionLabel = new javax.swing.JLabel();
        btnOpenReportForm = new javax.swing.JButton();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tester Dashboard - Bug Tracking System");
        setMinimumSize(new java.awt.Dimension(1000, 700));

        headerPanel.setBackground(new java.awt.Color(220, 53, 69));
        headerPanel.setPreferredSize(new java.awt.Dimension(1000, 60));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Bug Tracking System - Tester Panel");

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setText("Welcome, Tester");

        btnLogout.setBackground(new java.awt.Color(108, 117, 125));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(formListener);

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
                .addContainerGap(14, Short.MAX_VALUE))
        );

        mainTabbedPane.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        dashboardPanel.setBackground(new java.awt.Color(245, 245, 245));

        statsPanel.setOpaque(false);
        statsPanel.setLayout(new java.awt.GridLayout(1, 4, 20, 0));

        totalBugsPanel.setBackground(new java.awt.Color(0, 123, 255));

        lblTotalBugsTitle.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTotalBugsTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalBugsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalBugsTitle.setText("Total Bugs Reported");

        lblTotalBugsValue.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblTotalBugsValue.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalBugsValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalBugsValue.setText("0");

        javax.swing.GroupLayout totalBugsPanelLayout = new javax.swing.GroupLayout(totalBugsPanel);
        totalBugsPanel.setLayout(totalBugsPanelLayout);
        totalBugsPanelLayout.setHorizontalGroup(
            totalBugsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalBugsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(totalBugsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalBugsTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(lblTotalBugsValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        totalBugsPanelLayout.setVerticalGroup(
            totalBugsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalBugsPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTotalBugsTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalBugsValue)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        statsPanel.add(totalBugsPanel);

        openBugsPanel.setBackground(new java.awt.Color(255, 193, 7));

        lblOpenBugsTitle.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblOpenBugsTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblOpenBugsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOpenBugsTitle.setText("Open Bugs");

        lblOpenBugsValue.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblOpenBugsValue.setForeground(new java.awt.Color(255, 255, 255));
        lblOpenBugsValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOpenBugsValue.setText("0");

        javax.swing.GroupLayout openBugsPanelLayout = new javax.swing.GroupLayout(openBugsPanel);
        openBugsPanel.setLayout(openBugsPanelLayout);
        openBugsPanelLayout.setHorizontalGroup(
            openBugsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(openBugsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(openBugsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpenBugsTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(lblOpenBugsValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        openBugsPanelLayout.setVerticalGroup(
            openBugsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(openBugsPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblOpenBugsTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOpenBugsValue)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        statsPanel.add(openBugsPanel);

        inProgressPanel.setBackground(new java.awt.Color(23, 162, 184));

        lblInProgressTitle.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblInProgressTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblInProgressTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInProgressTitle.setText("In Progress");

        lblInProgressValue.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblInProgressValue.setForeground(new java.awt.Color(255, 255, 255));
        lblInProgressValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInProgressValue.setText("0");

        javax.swing.GroupLayout inProgressPanelLayout = new javax.swing.GroupLayout(inProgressPanel);
        inProgressPanel.setLayout(inProgressPanelLayout);
        inProgressPanelLayout.setHorizontalGroup(
            inProgressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inProgressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inProgressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInProgressTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(lblInProgressValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        inProgressPanelLayout.setVerticalGroup(
            inProgressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inProgressPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblInProgressTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInProgressValue)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        statsPanel.add(inProgressPanel);

        resolvedPanel.setBackground(new java.awt.Color(40, 167, 69));

        lblResolvedTitle.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblResolvedTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblResolvedTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResolvedTitle.setText("Resolved");

        lblResolvedValue.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblResolvedValue.setForeground(new java.awt.Color(255, 255, 255));
        lblResolvedValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResolvedValue.setText("0");

        javax.swing.GroupLayout resolvedPanelLayout = new javax.swing.GroupLayout(resolvedPanel);
        resolvedPanel.setLayout(resolvedPanelLayout);
        resolvedPanelLayout.setHorizontalGroup(
            resolvedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resolvedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resolvedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblResolvedTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(lblResolvedValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        resolvedPanelLayout.setVerticalGroup(
            resolvedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resolvedPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblResolvedTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblResolvedValue)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        statsPanel.add(resolvedPanel);

        bugsTablePanel.setBackground(new java.awt.Color(255, 255, 255));
        bugsTablePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        bugsTopPanel.setOpaque(false);

        lblMyBugs.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMyBugs.setText("My Reported Bugs");

        btnReportBug.setBackground(new java.awt.Color(40, 167, 69));
        btnReportBug.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReportBug.setForeground(new java.awt.Color(255, 255, 255));
        btnReportBug.setText("+ Report New Bug");
        btnReportBug.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReportBug.addActionListener(formListener);

        btnRefreshBugs.setText("Refresh");
        btnRefreshBugs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefreshBugs.addActionListener(formListener);

        filterLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterLabel.setText("Filter by Status:");

        filterComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filterComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Open", "In Progress", "Resolved", "Closed" }));
        filterComboBox.addActionListener(formListener);

        javax.swing.GroupLayout bugsTopPanelLayout = new javax.swing.GroupLayout(bugsTopPanel);
        bugsTopPanel.setLayout(bugsTopPanelLayout);
        bugsTopPanelLayout.setHorizontalGroup(
            bugsTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugsTopPanelLayout.createSequentialGroup()
                .addComponent(lblMyBugs)
                .addGap(18, 18, 18)
                .addComponent(filterLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefreshBugs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReportBug))
        );
        bugsTopPanelLayout.setVerticalGroup(
            bugsTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugsTopPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(bugsTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMyBugs)
                    .addComponent(btnReportBug)
                    .addComponent(btnRefreshBugs)
                    .addComponent(filterLabel)
                    .addComponent(filterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        bugsTable.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        bugsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Title", "Severity", "Status", "Assigned To", "Created Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        bugsTable.setRowHeight(30);
        bugsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bugsScrollPane.setViewportView(bugsTable);

        javax.swing.GroupLayout bugsTablePanelLayout = new javax.swing.GroupLayout(bugsTablePanel);
        bugsTablePanel.setLayout(bugsTablePanelLayout);
        bugsTablePanelLayout.setHorizontalGroup(
            bugsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugsTablePanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(bugsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bugsScrollPane)
                    .addComponent(bugsTopPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        bugsTablePanelLayout.setVerticalGroup(
            bugsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bugsTablePanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(bugsTopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bugsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bugsTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(statsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(bugsTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        mainTabbedPane.addTab("Dashboard", dashboardPanel);

        reportBugPanel.setBackground(new java.awt.Color(245, 245, 245));

        reportBugLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        reportBugLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportBugLabel.setText("Report a New Bug");

        reportBugInstructionLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        reportBugInstructionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportBugInstructionLabel.setText("Click the button below to open the bug reporting form");

        btnOpenReportForm.setBackground(new java.awt.Color(40, 167, 69));
        btnOpenReportForm.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnOpenReportForm.setForeground(new java.awt.Color(255, 255, 255));
        btnOpenReportForm.setText("Open Report Bug Form");
        btnOpenReportForm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOpenReportForm.setPreferredSize(new java.awt.Dimension(250, 50));
        btnOpenReportForm.addActionListener(formListener);

        javax.swing.GroupLayout reportBugPanelLayout = new javax.swing.GroupLayout(reportBugPanel);
        reportBugPanel.setLayout(reportBugPanelLayout);
        reportBugPanelLayout.setHorizontalGroup(
            reportBugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportBugPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reportBugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reportBugLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addComponent(reportBugInstructionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(reportBugPanelLayout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addComponent(btnOpenReportForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        reportBugPanelLayout.setVerticalGroup(
            reportBugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportBugPanelLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(reportBugLabel)
                .addGap(18, 18, 18)
                .addComponent(reportBugInstructionLabel)
                .addGap(50, 50, 50)
                .addComponent(btnOpenReportForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(251, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab("Report Bug", reportBugPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == btnLogout) {
                TesterDashboard.this.btnLogoutActionPerformed(evt);
            }
            else if (evt.getSource() == btnReportBug) {
                TesterDashboard.this.btnReportBugActionPerformed(evt);
            }
            else if (evt.getSource() == btnRefreshBugs) {
                TesterDashboard.this.btnRefreshBugsActionPerformed(evt);
            }
            else if (evt.getSource() == filterComboBox) {
                TesterDashboard.this.filterComboBoxActionPerformed(evt);
            }
            else if (evt.getSource() == btnOpenReportForm) {
                TesterDashboard.this.btnOpenReportFormActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnReportBugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportBugActionPerformed
        openReportBugForm();
    }//GEN-LAST:event_btnReportBugActionPerformed

    private void btnRefreshBugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshBugsActionPerformed
        refreshDashboard();
    }//GEN-LAST:event_btnRefreshBugsActionPerformed

    private void filterComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterComboBoxActionPerformed
        filterBugs();
    }//GEN-LAST:event_filterComboBoxActionPerformed

    private void btnOpenReportFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenReportFormActionPerformed
        openReportBugForm();
    }//GEN-LAST:event_btnOpenReportFormActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnOpenReportForm;
    private javax.swing.JButton btnRefreshBugs;
    private javax.swing.JButton btnReportBug;
    private javax.swing.JScrollPane bugsScrollPane;
    private javax.swing.JTable bugsTable;
    private javax.swing.JPanel bugsTablePanel;
    private javax.swing.JPanel bugsTopPanel;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JComboBox<String> filterComboBox;
    private javax.swing.JLabel filterLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel lblInProgressTitle;
    private javax.swing.JLabel lblInProgressValue;
    private javax.swing.JLabel lblMyBugs;
    private javax.swing.JLabel lblOpenBugsTitle;
    private javax.swing.JLabel lblOpenBugsValue;
    private javax.swing.JLabel lblResolvedTitle;
    private javax.swing.JLabel lblResolvedValue;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalBugsTitle;
    private javax.swing.JLabel lblTotalBugsValue;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel inProgressPanel;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JPanel openBugsPanel;
    private javax.swing.JPanel reportBugPanel;
    private javax.swing.JLabel reportBugLabel;
    private javax.swing.JLabel reportBugInstructionLabel;
    private javax.swing.JPanel resolvedPanel;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JPanel totalBugsPanel;
    // End of variables declaration//GEN-END:variables

    private void initializeDashboard() {
        if (currentTester != null) {
            lblWelcome.setText("Welcome, " + currentTester.getFullName());
        }
        refreshDashboard();
    }

    private void refreshDashboard() {
        if (currentTester == null) return;
        
        BugDAO bugDAO = new BugDAO();
        bugDAO.refresh();
        
        List<Bug> myBugs = bugDAO.findByReporter(currentTester.getId());
        
        // Update statistics
        int totalBugs = myBugs.size();
        int openBugs = countBugsByStatus(myBugs, BugStatusEnum.OPEN);
        int inProgressBugs = countBugsByStatus(myBugs, BugStatusEnum.IN_PROGRESS);
        int resolvedBugs = countBugsByStatus(myBugs, BugStatusEnum.RESOLVED) + 
                          countBugsByStatus(myBugs, BugStatusEnum.CLOSED);
        
        lblTotalBugsValue.setText(String.valueOf(totalBugs));
        lblOpenBugsValue.setText(String.valueOf(openBugs));
        lblInProgressValue.setText(String.valueOf(inProgressBugs));
        lblResolvedValue.setText(String.valueOf(resolvedBugs));
        
        // Update bugs table
        updateBugsTable(myBugs);
    }

    private int countBugsByStatus(List<Bug> bugs, BugStatusEnum status) {
        int count = 0;
        for (Bug bug : bugs) {
            if (bug.getStatus() == status) {
                count++;
            }
        }
        return count;
    }

    private void updateBugsTable(List<Bug> bugs) {
        DefaultTableModel model = (DefaultTableModel) bugsTable.getModel();
        model.setRowCount(0);
        
        UserDAO userDAO = new UserDAO();
        
        for (Bug bug : bugs) {
            User assignedDev = userDAO.findById(bug.getAssignedDeveloperId());
            String devName = assignedDev != null ? assignedDev.getFullName() : "Unassigned";
            
            model.addRow(new Object[]{
                bug.getId(),
                bug.getTitle(),
                bug.getSeverity().getDisplayName(),
                bug.getStatus().getDisplayName(),
                devName,
                bug.getCreatedAt().toLocalDate().toString()
            });
        }
    }

    private void filterBugs() {
        if (currentTester == null) return;
        
        String selectedFilter = (String) filterComboBox.getSelectedItem();
        BugDAO bugDAO = new BugDAO();
        List<Bug> myBugs = bugDAO.findByReporter(currentTester.getId());
        
        if ("All".equals(selectedFilter)) {
            updateBugsTable(myBugs);
            return;
        }
        
        List<Bug> filteredBugs = new java.util.ArrayList<>();
        BugStatusEnum statusFilter = BugStatusEnum.fromString(selectedFilter);
        
        for (Bug bug : myBugs) {
            if (bug.getStatus() == statusFilter) {
                filteredBugs.add(bug);
            }
        }
        
        updateBugsTable(filteredBugs);
    }

    private void openReportBugForm() {
        if (currentTester == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error: No tester logged in.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ReportBugForm reportForm = new ReportBugForm(this, true, currentTester);
        reportForm.setVisible(true);
        
        if (reportForm.isBugReported()) {
            refreshDashboard();
            mainTabbedPane.setSelectedIndex(0); // Switch to dashboard tab
        }
    }

    // Dummy method to simulate getting logged-in user
    // This should be replaced with actual authentication logic
    public static User getLoggedInUser() {
        UserDAO userDAO = new UserDAO();
        // Find first tester in system for demo purposes
        List<User> testers = userDAO.findByRole(Role.TESTER);
        if (!testers.isEmpty()) {
            return testers.get(0);
        }
        return null;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            User tester = getLoggedInUser();
            if (tester != null) {
                new TesterDashboard(tester).setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null,
                    "No tester found in system. Please create a tester account first.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}