package dao;

import models.Bug;
import config.AppConfig;
import enums.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Bug file operations
 */
public class BugDAO {

    private static final String FILE_PATH = AppConfig.BUGS_FILE;
    private static final String DELIMITER = "\\|";
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    
    private static List<Bug> bugsCache = new ArrayList<>();

    public BugDAO() {
        ensureFileExists();
        loadFromFile();
    }

    // ==================== CREATE ====================

    public Bug save(Bug bug) {
        bug.setId(generateNextId());
        bug.setCreatedAt(LocalDateTime.now());
        bug.setUpdatedAt(LocalDateTime.now());
        bugsCache.add(bug);
        saveToFile();
        return bug;
    }

    /**
     * Add a new bug (alias for save)
     */
    public boolean addBug(Bug bug) {
        try {
            save(bug);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding bug: " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================

    public Bug findById(int id) {
        for (Bug bug : bugsCache) {
            if (bug.getId() == id) return bug;
        }
        return null;
    }

    public List<Bug> findAll() {
        return new ArrayList<>(bugsCache);
    }

    public List<Bug> findByStatus(BugStatus status) {
        List<Bug> result = new ArrayList<>();
        for (Bug bug : bugsCache) {
            if (bug.getStatus() == status) result.add(bug);
        }
        return result;
    }

    public List<Bug> findByAssigneeId(int developerId) {
        List<Bug> result = new ArrayList<>();
        for (Bug bug : bugsCache) {
            if (bug.getAssigneeId() == developerId) result.add(bug);
        }
        return result;
    }

    public List<Bug> findByReporterId(int testerId) {
        List<Bug> result = new ArrayList<>();
        for (Bug bug : bugsCache) {
            if (bug.getReporterId() == testerId) result.add(bug);
        }
        return result;
    }

    public List<Bug> findOpenBugs() {
        List<Bug> result = new ArrayList<>();
        for (Bug bug : bugsCache) {
            if (bug.isOpen()) result.add(bug);
        }
        return result;
    }

    public List<Bug> findClosedBugs() {
        List<Bug> result = new ArrayList<>();
        for (Bug bug : bugsCache) {
            if (bug.isClosed()) result.add(bug);
        }
        return result;
    }

    // ==================== UPDATE ====================

    public boolean update(Bug bug) {
        for (int i = 0; i < bugsCache.size(); i++) {
            if (bugsCache.get(i).getId() == bug.getId()) {
                bug.setUpdatedAt(LocalDateTime.now());
                bugsCache.set(i, bug);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // ==================== DELETE ====================

    public boolean delete(int id) {
        for (int i = 0; i < bugsCache.size(); i++) {
            if (bugsCache.get(i).getId() == id) {
                bugsCache.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // ==================== COUNT ====================

    public int count() { return bugsCache.size(); }

    public int countByStatus(BugStatus status) {
        int count = 0;
        for (Bug bug : bugsCache) {
            if (bug.getStatus() == status) count++;
        }
        return count;
    }

    public int countByAssignee(int developerId) {
        int count = 0;
        for (Bug bug : bugsCache) {
            if (bug.getAssigneeId() == developerId) count++;
        }
        return count;
    }

    public int countByReporter(int testerId) {
        int count = 0;
        for (Bug bug : bugsCache) {
            if (bug.getReporterId() == testerId) count++;
        }
        return count;
    }

    // ==================== FILE OPERATIONS ====================

    private void loadFromFile() {
        bugsCache.clear();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return;
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || line.startsWith("id")) continue;
                
                Bug bug = parseBug(line);
                if (bug != null) bugsCache.add(bug);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading bugs: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try {
            PrintWriter writer = new PrintWriter(FILE_PATH);
            writer.println("id|title|description|projectName|bugType|priority|bugLevel|status|reporterId|assigneeId|screenshotPath|bugDate|createdAt|updatedAt");
            
            for (Bug bug : bugsCache) {
                writer.println(formatBug(bug));
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving bugs: " + e.getMessage());
        }
    }

    private Bug parseBug(String line) {
        try {
            String[] f = line.split(DELIMITER);
            if (f.length < 14) return null;
            
            Bug bug = new Bug();
            bug.setId(Integer.parseInt(f[0]));
            bug.setTitle(f[1]);
            bug.setDescription(f[2]);
            bug.setProjectName(f[3]);
            bug.setBugType(BugType.valueOf(f[4]));
            bug.setPriority(BugPriority.valueOf(f[5]));
            bug.setBugLevel(BugLevel.valueOf(f[6]));
            bug.setStatus(BugStatus.valueOf(f[7]));
            bug.setReporterId(Integer.parseInt(f[8]));
            bug.setAssigneeId(Integer.parseInt(f[9]));
            bug.setScreenshotPath(f[10].equals("null") ? null : f[10]);
            bug.setBugDate(LocalDate.parse(f[11], DATE_FORMAT));
            bug.setCreatedAt(LocalDateTime.parse(f[12], DATE_TIME_FORMAT));
            bug.setUpdatedAt(LocalDateTime.parse(f[13], DATE_TIME_FORMAT));
            return bug;
        } catch (Exception e) {
            return null;
        }
    }

    private String formatBug(Bug bug) {
        return bug.getId() + "|" +
               bug.getTitle() + "|" +
               bug.getDescription() + "|" +
               bug.getProjectName() + "|" +
               bug.getBugType() + "|" +
               bug.getPriority() + "|" +
               (bug.getBugLevel() != null ? bug.getBugLevel() : BugLevel.MINOR) + "|" +
               bug.getStatus() + "|" +
               bug.getReporterId() + "|" +
               bug.getAssigneeId() + "|" +
               (bug.getScreenshotPath() != null ? bug.getScreenshotPath() : "null") + "|" +
               bug.getBugDate().format(DATE_FORMAT) + "|" +
               bug.getCreatedAt().format(DATE_TIME_FORMAT) + "|" +
               bug.getUpdatedAt().format(DATE_TIME_FORMAT);
    }

    private void ensureFileExists() {
        try {
            File file = new File(FILE_PATH);
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    private int generateNextId() {
        int maxId = 0;
        for (Bug bug : bugsCache) {
            if (bug.getId() > maxId) maxId = bug.getId();
        }
        return maxId + 1;
    }

    /**
     * Get the next available ID for a new bug (public method)
     */
    public int getNextId() {
        return generateNextId();
    }

    public void refresh() {
        loadFromFile();
    }
}
