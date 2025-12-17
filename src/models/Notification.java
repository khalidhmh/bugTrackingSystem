/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.time.LocalDateTime;

public class Notification {

    // ==================== Fields ====================
    private int id;
    private User sender;
    private User recipient;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;

    // ==================== Constructors ====================
    public Notification(User sender, User recipient, String message) {
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient cannot be null");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        this.sender = sender;
        this.recipient = recipient;
        this.message = message.trim();
        this.isRead = false;
        this.createdAt = LocalDateTime.now();
    }

    // ==================== Getters ====================
    public int getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ==================== Business Helpers ====================

    public void markAsRead() {
        this.isRead = true;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "from=" + (sender != null ? sender.getUsername() : "System") +
                ", message='" + message + '\'' +
                ", read=" + isRead +
                '}';
    }
}
