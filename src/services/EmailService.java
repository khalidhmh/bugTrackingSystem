package services;

import dao.NotificationDAO;

public class EmailService {

    private final NotificationDAO notificationDAO;

    public EmailService() {
        this.notificationDAO = new NotificationDAO();
    }

    public void sendEmail(String recipientId, String senderId, String subject, String body) {
        // 1. تجهيز نص الرسالة
        String fullMessage = subject + " - " + body;

        // 2. محاكاة الإرسال (طباعة في الكونسول)
        System.out.println(">> Email Sent to User ID: " + recipientId);
        System.out.println(">> Subject: " + subject);

        // 3. الحفظ الفعلي في الداتا بيز (ملف التكست)
        notificationDAO.save(recipientId, senderId, fullMessage);
    }
}