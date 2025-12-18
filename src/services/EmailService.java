package services;

import dao.NotificationDAO;
import dao.UserDAO;
import models.Bug;
import models.User;

/**
 * Email/Notification service - sends notifications
 */
public class EmailService {

    private final NotificationDAO notificationDAO;
    private final UserDAO userDAO;

    public EmailService() {
        this.notificationDAO = new NotificationDAO();
        this.userDAO = new UserDAO();
    }

    /**
     * Send notification when bug is assigned to developer
     */
    public void sendBugAssignmentNotification(Bug bug, int testerId) {
        User developer = userDAO.findById(bug.getAssigneeId());
        if (developer == null) return;

        String message = "New Bug Assigned: " + bug.getTitle() + 
                        " | Project: " + bug.getProjectName() + 
                        " | Priority: " + bug.getPriority();
        
        notificationDAO.save(
            String.valueOf(developer.getId()),
            String.valueOf(testerId),
            message
        );
        
        System.out.println(">> Email sent to " + developer.getEmail() + ": " + message);
    }

    /**
     * Send notification when developer changes bug status
     */
    public void sendBugStatusChangeNotification(Bug bug, int developerId) {
        User tester = userDAO.findById(bug.getReporterId());
        if (tester == null) return;

        String message = "Bug Status Updated: " + bug.getTitle() + 
                        " | New Status: " + bug.getStatus();
        
        notificationDAO.save(
            String.valueOf(tester.getId()),
            String.valueOf(developerId),
            message
        );
        
        System.out.println(">> Email sent to " + tester.getEmail() + ": " + message);
    }

    /**
     * Send generic notification
     */
    public void sendNotification(int recipientId, int senderId, String message) {
        notificationDAO.save(
            String.valueOf(recipientId),
            String.valueOf(senderId),
            message
        );
    }

    /**
     * Static method to send email asynchronously (simulated)
     * In a real app, this would connect to an SMTP server
     */
    public static void sendEmailAsync(String toEmail, String subject, String body) {
        new Thread(() -> {
            try {
                // Simulate sending email
                System.out.println("=========================================");
                System.out.println("SENDING EMAIL (simulated)");
                System.out.println("To: " + toEmail);
                System.out.println("Subject: " + subject);
                System.out.println("-----------------------------------------");
                System.out.println(body);
                System.out.println("=========================================");
                
                // Store as notification for in-app tracking
                NotificationDAO notifDAO = new NotificationDAO();
                UserDAO uDao = new UserDAO();
                User recipient = uDao.getUserByEmail(toEmail);
                if (recipient != null) {
                    User sender = SessionManager.getInstance().getCurrentUser();
                    int senderId = sender != null ? sender.getId() : 0;
                    notifDAO.save(
                        String.valueOf(recipient.getId()),
                        String.valueOf(senderId),
                        subject + ": " + body.substring(0, Math.min(body.length(), 100)) + "..."
                    );
                }
            } catch (Exception e) {
                System.err.println("Email sending failed: " + e.getMessage());
            }
        }).start();
    }
}
