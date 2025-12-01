package com.bugtracking.model;

public class Notification {
    private String recipientId;
    private String senderId;
    private String message;
    private boolean isRead;

    public Notification(String recipientId, String senderId, String message, boolean isRead) {
        this.recipientId = recipientId;
        this.senderId = senderId;
        this.message = message;
        this.isRead = isRead;
    }

    public String getMessage() { return message; }
    public String getSenderId() { return senderId; }

    // Getters for display
    @Override
    public String toString() {
        return "From " + senderId + ": " + message;
    }
}