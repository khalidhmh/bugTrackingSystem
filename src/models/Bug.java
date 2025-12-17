package models;

/**
 * Bug entity model
 * Fields: id, title, description, projectName, status, priority, reporterId, assigneeId, createdDate, updatedDate
 * @author Team
 */


import java.time.LocalDateTime;

enum BugPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
enum BugStatus {
    OPEN,
    IN_PROGRESS,
    FIXED,
    CLOSED
}

public class Bug {

    // ==================== Fields ====================
    private int id;
    private String title;
    private String description;
    private BugPriority priority;
    private BugStatus status;

    private User reporter;
    private User assignee;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ==================== Constructors ====================

    public Bug() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = BugStatus.OPEN;
    }

    public Bug(String title, String description,
               BugPriority priority,
               User reporter,
               User assignee) {
        this();
        setTitle(title);
        setDescription(description);
        setPriority(priority);
        this.reporter = reporter;
        this.assignee = assignee;
    }

    // ==================== Getters & Setters ====================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Bug ID cannot be negative");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Bug title cannot be empty");
        }
        this.title = title.trim();
        updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Bug description cannot be empty");
        }
        this.description = description.trim();
        updateTimestamp();
    }

    public BugPriority getPriority() {
        return priority;
    }

    public void setPriority(BugPriority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        this.priority = priority;
        updateTimestamp();
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
        updateTimestamp();
    }

    public User getReporter() {
        return reporter;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    // ==================== Object Methods ====================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bug)) return false;
        Bug bug = (Bug) o;
        return id == bug.id;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", reporter=" + reporter.getUsername() +
                ", assignee=" + (assignee != null ? assignee.getUsername() : "Unassigned") +
                '}';
    }
}
