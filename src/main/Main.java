package main;

import config.AppConfig;
import ui.common.LoginForm;

import javax.swing.*;
import java.io.File;

/**
 * Application entry point
 * @author Team
 */
public class Main {
    
    public static void main(String[] args) {
        // Ensure data directories exist
        ensureDataDirectoriesExist();
        
        // Set Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Use default look and feel
            System.err.println("Could not set Nimbus look and feel");
        }
        
        // Start application - show login form
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            
            System.out.println("═══════════════════════════════════════════════════");
            System.out.println("  " + AppConfig.APP_NAME + " v" + AppConfig.APP_VERSION);
            System.out.println("  Application started successfully");
            System.out.println("  Default login: admin / admin123");
            System.out.println("═══════════════════════════════════════════════════");
        });
    }
    
    /**
     * Ensure all required data directories exist
     */
    private static void ensureDataDirectoriesExist() {
        // Create data folder
        File dataFolder = new File(AppConfig.DATA_FOLDER);
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
            System.out.println("Created data directory: " + dataFolder.getAbsolutePath());
        }
        
        // Create screenshots folder
        File screenshotsFolder = new File(AppConfig.SCREENSHOTS_FOLDER);
        if (!screenshotsFolder.exists()) {
            screenshotsFolder.mkdirs();
            System.out.println("Created screenshots directory: " + screenshotsFolder.getAbsolutePath());
        }
    }
}
