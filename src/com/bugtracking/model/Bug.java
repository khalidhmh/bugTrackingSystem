package com.bugtracking.model;

import java.io.Serializable;

public class Bug implements Serializable {
    private String id;
    private String title;
    private String description;
    private String priority; // High, Medium, Low
    private String status;   // Open, Closed, Fixed
    private String reporterId;
    private String assigneeId; // The Developer's ID
    private String imagePath;  // Path to the screenshot

    public Bug(String id, String title, String description, String priority, String status, String reporterId, String assigneeId, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
        this.imagePath = imagePath;
    }

    // --- GETTERS ---
    public String getId() { return id; }
    public String getTitle() { return title; }

    // THIS WAS MISSING:
    public String getDescription() { return description; }

    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public String getReporterId() { return reporterId; }
    public String getAssigneeId() { return assigneeId; }
    public String getImagePath() { return imagePath; }

    // --- SETTERS ---
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        // Format: ID;Title;Desc;Priority;Status;ReporterID;AssigneeID;ImagePath
        // Note: We replace semicolons in description to avoid parsing errors
        String safeDesc = description != null ? description.replace(";", ",") : "";
        return id + ";" + title + ";" + safeDesc + ";" + priority + ";" + status + ";" + reporterId + ";" + assigneeId + ";" + imagePath;
    }
}