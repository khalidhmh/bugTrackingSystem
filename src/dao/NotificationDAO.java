package dao;

import config.AppConfig;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Notification file operations
 * File format: id|recipientId|senderId|message|isRead|createdAt
 * 
 * @author Team
 */
public class NotificationDAO {

    private static final String FILE_PATH = AppConfig.NOTIFICATIONS_FILE;
    private static final String DELIMITER = ";";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // ==================== Constructor ====================

    public NotificationDAO() {
        createFileIfNotExists();
    }

    // ==================== CREATE ====================

    /**
     * Save a new notification
     * 
     * @param recipientId The ID of the user receiving the notification
     * @param senderId The ID of the user sending the notification (0 for system)
     * @param message The notification message
     */
    public void save(String recipientId, String senderId, String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            int nextId = getNextId();
            String createdAt = LocalDateTime.now().format(DATE_FORMAT);
            
            // Format: id;recipientId;senderId;message;isRead;createdAt
            String line = nextId + DELIMITER + 
                         recipientId + DELIMITER + 
                         senderId + DELIMITER + 
                         escapeMessage(message) + DELIMITER + 
                         "false" + DELIMITER + 
                         createdAt;
            
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving notification: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== READ ====================

    /**
     * Get raw notification lines for a user
     * 
     * @param userId The user ID
     * @return List of raw notification lines
     */
    public List<String> getNotificationsRaw(int userId) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 5 && parts[1].equals(String.valueOf(userId))) {
                    list.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading notifications: " + e.getMessage());
        }
        return list;
    }

    /**
     * Get all notifications for a user
     * 
     * @param userId The user ID
     * @return List of notification details (senderId, message, isRead, createdAt)
     */
    public List<NotificationInfo> getNotifications(int userId) {
        List<NotificationInfo> notifications = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 6 && parts[1].equals(String.valueOf(userId))) {
                    NotificationInfo info = new NotificationInfo();
                    info.id = Integer.parseInt(parts[0]);
                    info.recipientId = Integer.parseInt(parts[1]);
                    info.senderId = Integer.parseInt(parts[2]);
                    info.message = unescapeMessage(parts[3]);
                    info.isRead = Boolean.parseBoolean(parts[4]);
                    info.createdAt = LocalDateTime.parse(parts[5], DATE_FORMAT);
                    notifications.add(info);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading notifications: " + e.getMessage());
        }
        return notifications;
    }

    /**
     * Get unread notification count for a user
     * 
     * @param userId The user ID
     * @return Count of unread notifications
     */
    public int getUnreadCount(int userId) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 5 && 
                    parts[1].equals(String.valueOf(userId)) && 
                    parts[4].equals("false")) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error counting notifications: " + e.getMessage());
        }
        return count;
    }

    // ==================== UPDATE ====================

    /**
     * Mark all notifications as read for a user
     * 
     * @param userId The user ID
     */
    public void markAllAsRead(int userId) {
        List<String> lines = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    lines.add(line);
                    continue;
                }
                
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 5 && parts[1].equals(String.valueOf(userId))) {
                    parts[4] = "true";
                    line = String.join(DELIMITER, parts);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading notifications: " + e.getMessage());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating notifications: " + e.getMessage());
        }
    }

    /**
     * Mark a specific notification as read
     * 
     * @param notificationId The notification ID
     */
    public void markAsRead(int notificationId) {
        List<String> lines = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    lines.add(line);
                    continue;
                }
                
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 5 && parts[0].equals(String.valueOf(notificationId))) {
                    parts[4] = "true";
                    line = String.join(DELIMITER, parts);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading notifications: " + e.getMessage());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating notifications: " + e.getMessage());
        }
    }

    // ==================== DELETE ====================

    /**
     * Delete all notifications for a user
     * 
     * @param userId The user ID
     */
    public void deleteAllForUser(int userId) {
        List<String> lines = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(DELIMITER);
                // Keep lines that are NOT for this user
                if (parts.length < 2 || !parts[1].equals(String.valueOf(userId))) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading notifications: " + e.getMessage());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error deleting notifications: " + e.getMessage());
        }
    }

    // ==================== HELPER METHODS ====================

    /**
     * Create the notification file if it doesn't exist
     */
    private void createFileIfNotExists() {
        try {
            File file = new File(FILE_PATH);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating notifications file: " + e.getMessage());
        }
    }

    /**
     * Get the next available ID
     */
    private int getNextId() {
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(DELIMITER);
                if (parts.length > 0) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Skip invalid lines
                    }
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return maxId + 1;
    }

    /**
     * Escape special characters in message
     */
    private String escapeMessage(String message) {
        if (message == null) return "";
        return message.replace(";", "\\;").replace("\n", "\\n").replace("\r", "");
    }

    /**
     * Unescape special characters in message
     */
    private String unescapeMessage(String message) {
        if (message == null) return "";
        return message.replace("\\;", ";").replace("\\n", "\n");
    }

    // ==================== INNER CLASS ====================

    /**
     * Helper class to hold notification information
     */
    public static class NotificationInfo {
        public int id;
        public int recipientId;
        public int senderId;
        public String message;
        public boolean isRead;
        public LocalDateTime createdAt;
        
        @Override
        public String toString() {
            return "Notification{" +
                   "id=" + id +
                   ", from=" + senderId +
                   ", message='" + message + '\'' +
                   ", read=" + isRead +
                   '}';
        }
    }
}
