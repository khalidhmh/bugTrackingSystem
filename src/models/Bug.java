package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import enums.BugPriority;
import enums.BugStatus;
import enums.BugType;
import enums.BugLevel;

/**
 * Bug entity model
 */
public class Bug {

    private int id;
    private String title;
    private String description;
    private String projectName;
    private BugType bugType;
    private BugPriority priority;
    private BugLevel bugLevel;
    private BugStatus status;
    
    private int reporterId;      // Tester who reported
    private int assigneeId;      // Developer assigned
    
    private String screenshotPath;
    private LocalDate bugDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ==================== Constructors ====================

    public Bug() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = BugStatus.OPEN;
        this.bugDate = LocalDate.now();
        this.assigneeId = -1;
    }

    public Bug(String title, String description, String projectName,
               BugType bugType, BugPriority priority, int reporterId) {
        this();
        this.title = title;
        this.description = description;
        this.projectName = projectName;
        this.bugType = bugType;
        this.priority = priority;
        this.reporterId = reporterId;
    }

    // ==================== Getters & Setters ====================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { 
        this.title = title; 
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { 
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { 
        this.projectName = projectName;
        this.updatedAt = LocalDateTime.now();
    }

    public BugType getBugType() { return bugType; }
    public void setBugType(BugType bugType) { 
        this.bugType = bugType;
        this.updatedAt = LocalDateTime.now();
    }

    public BugPriority getPriority() { return priority; }
    public void setPriority(BugPriority priority) { 
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }

    public BugLevel getBugLevel() { return bugLevel; }
    public void setBugLevel(BugLevel bugLevel) { 
        this.bugLevel = bugLevel;
        this.updatedAt = LocalDateTime.now();
    }

    public BugStatus getStatus() { return status; }
    public void setStatus(BugStatus status) { 
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public int getReporterId() { return reporterId; }
    public void setReporterId(int reporterId) { this.reporterId = reporterId; }

    public int getAssigneeId() { return assigneeId; }
    public void setAssigneeId(int assigneeId) { 
        this.assigneeId = assigneeId;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isAssigned() { return assigneeId > 0; }

    public String getScreenshotPath() { return screenshotPath; }
    public void setScreenshotPath(String screenshotPath) { 
        this.screenshotPath = screenshotPath;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean hasScreenshot() { 
        return screenshotPath != null && !screenshotPath.isEmpty(); 
    }

    public LocalDate getBugDate() { return bugDate; }
    public void setBugDate(LocalDate bugDate) { this.bugDate = bugDate; }
    public void setBugDate(String bugDateStr) { 
        if (bugDateStr != null && !bugDateStr.isEmpty()) {
            this.bugDate = LocalDate.parse(bugDateStr);
        }
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ==================== Helper Methods ====================

    public boolean isOpen() {
        return status == BugStatus.OPEN || status == BugStatus.IN_PROGRESS;
    }

    public boolean isClosed() {
        return status == BugStatus.CLOSED;
    }

    @Override
    public String toString() {
        return "Bug #" + id + ": " + title + " [" + status + "]";
    }
}
