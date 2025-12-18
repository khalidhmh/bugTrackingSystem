package services;

import dao.BugDAO;
import dao.UserDAO;
import enums.*;
import models.Bug;
import models.User;
import java.util.List;

/**
 * Bug service - business logic for bug management
 */
public class BugService {

    private final BugDAO bugDAO;
    private final UserDAO userDAO;
    private final EmailService emailService;

    public BugService() {
        this.bugDAO = new BugDAO();
        this.userDAO = new UserDAO();
        this.emailService = new EmailService();
    }

    // ==================== TESTER OPERATIONS ====================

    /**
     * Create a new bug (Tester)
     */
    public Bug createBug(String title, String description, String projectName,
                        BugType bugType, BugPriority priority, int reporterId,
                        String screenshotPath) {
        
        Bug bug = new Bug(title, description, projectName, bugType, priority, reporterId);
        if (screenshotPath != null && !screenshotPath.isEmpty()) {
            bug.setScreenshotPath(screenshotPath);
        }
        return bugDAO.save(bug);
    }

    /**
     * Assign bug to developer (Tester)
     */
    public boolean assignBugToDeveloper(int bugId, int developerId, int testerId) {
        Bug bug = bugDAO.findById(bugId);
        if (bug == null) return false;

        User developer = userDAO.findById(developerId);
        if (developer == null || developer.getRole() != Role.DEVELOPER) return false;

        bug.setAssigneeId(developerId);
        bug.setStatus(BugStatus.IN_PROGRESS);
        
        boolean updated = bugDAO.update(bug);
        if (updated) {
            emailService.sendBugAssignmentNotification(bug, testerId);
        }
        return updated;
    }

    /**
     * Close a resolved bug (Tester)
     */
    public boolean closeBug(int bugId) {
        Bug bug = bugDAO.findById(bugId);
        if (bug == null || bug.getStatus() != BugStatus.RESOLVED) return false;
        
        bug.setStatus(BugStatus.CLOSED);
        return bugDAO.update(bug);
    }

    /**
     * Get bugs reported by tester
     */
    public List<Bug> getBugsByReporter(int testerId) {
        return bugDAO.findByReporterId(testerId);
    }

    // ==================== DEVELOPER OPERATIONS ====================

    /**
     * Get bugs assigned to developer
     */
    public List<Bug> getAssignedBugs(int developerId) {
        return bugDAO.findByAssigneeId(developerId);
    }

    /**
     * Mark bug as resolved (Developer)
     */
    public boolean markBugAsResolved(int bugId, int developerId) {
        Bug bug = bugDAO.findById(bugId);
        if (bug == null || bug.getAssigneeId() != developerId) return false;
        
        bug.setStatus(BugStatus.RESOLVED);
        boolean updated = bugDAO.update(bug);
        
        if (updated) {
            emailService.sendBugStatusChangeNotification(bug, developerId);
        }
        return updated;
    }

    // ==================== PM/ADMIN OPERATIONS ====================

    /**
     * Get all bugs
     */
    public List<Bug> getAllBugs() {
        return bugDAO.findAll();
    }

    /**
     * Get open bugs
     */
    public List<Bug> getOpenBugs() {
        return bugDAO.findOpenBugs();
    }

    /**
     * Get closed bugs
     */
    public List<Bug> getClosedBugs() {
        return bugDAO.findClosedBugs();
    }

    /**
     * Get bugs by status
     */
    public List<Bug> getBugsByStatus(BugStatus status) {
        return bugDAO.findByStatus(status);
    }

    /**
     * Get bug by ID
     */
    public Bug getBugById(int id) {
        return bugDAO.findById(id);
    }

    /**
     * Delete bug (Admin)
     */
    public boolean deleteBug(int bugId) {
        return bugDAO.delete(bugId);
    }

    // ==================== STATISTICS ====================

    public int getTotalBugsCount() {
        return bugDAO.count();
    }

    public int getOpenBugsCount() {
        return bugDAO.countByStatus(BugStatus.OPEN) + bugDAO.countByStatus(BugStatus.IN_PROGRESS);
    }

    public int getResolvedBugsCount() {
        return bugDAO.countByStatus(BugStatus.RESOLVED);
    }

    public int getClosedBugsCount() {
        return bugDAO.countByStatus(BugStatus.CLOSED);
    }

    public int getBugsCountByDeveloper(int developerId) {
        return bugDAO.countByAssignee(developerId);
    }

    public int getBugsCountByTester(int testerId) {
        return bugDAO.countByReporter(testerId);
    }
}
