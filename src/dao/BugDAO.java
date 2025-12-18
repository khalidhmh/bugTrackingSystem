package dao;

import models.Bug;
import models.Role;
import models.User;
import enums.*;
import java.lang.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Data Access Object for Bug file operations
 * File: data/bugs.txt
 * @author Team
 */
public class BugDAO {

    // TODO: Implement CRUD operations for bugs
    // TODO: Read/write to bugs.txt file
    private static final String FILE_PATH = "data/bugs.txt";
    private static final String DELIMITER = "\\|";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final String HEADER = "id" + DELIMITER
            + "title" + DELIMITER
            + "description" + DELIMITER
            + "projectName" + DELIMITER
            + "status" + DELIMITER
            + "priority" + DELIMITER
            + "reporterId" + DELIMITER
            + "assigneeId" + DELIMITER
            + "createdDate" + DELIMITER
            + "updatedDate";

    private static List<Bug> bugsCache = new ArrayList<Bug>();

    // ==================== Constructor ====================

    public BugDAO() {
        loadFromFile();
    }


    // ==================== CREATE ====================

    /**
     * Save a new bug to the file
     *
     * @param bug The bug to save (ID will be auto-generated)
     * @return The saved bug with generated ID
     */
    public Bug save(Bug bug) {
        // Generate ID
        bug.setId(generateNextId());
        // Add to cache and save
        bugsCache.add(bug);
        saveToFile();

        return bug;
    }


    // ==================== READ ====================

    /**
     * Find bug by ID
     *
     * @param id The bug ID
     * @return bug if found, null otherwise
     */
    public Bug findById(int id) {
        for (Bug bug : bugsCache) {
            if (bug.getId() == id) {
                return bug;
            }
        }
        return null;
    }


    /**
     * Find bug by title
     *
     * @param title The title to search
     * @return title if found, null otherwise
     */
    public Bug findByTitle(String title) {
        for (Bug bug : bugsCache) {
            if (bug.getTitle().equalsIgnoreCase(title)) {
                return bug;
            }
        }
        return null;
    }

    /**
     * Get all bugs
     * @return List of all users
     */
    public List<Bug> findAllBugs() {
        return new ArrayList<>(bugsCache);
    }

    /**
     * Search bugs by title, id
     * @param searchTerm The search term
     * @return List of matching bugs
     */
    public List<Bug> search(String searchTerm) {
        List<Bug> result = new ArrayList<>();
        String term = searchTerm.toLowerCase();

        for (Bug bug : bugsCache) {
            if (bug.getTitle().toLowerCase().contains(term) || Integer.parseInt(term) == bug.getId()){
                result.add(bug);
            }
        }
        return result;
    }


    // ==================== UPDATE ====================

    /**
     * Update an existing user
     *
     * @param bug The user with updated data
     * @return true if updated, false if user not found
     */
    public boolean update(Bug bug) {
        for (int i = 0; i < bugsCache.size(); i++) {
            if (bugsCache.get(i).getId() == bug.getId()) {
                // Check unique constraints
                Bug existingByTitle = findByTitle(bug.getTitle());
                if (existingByTitle != null && existingByTitle.getId() != bug.getId()) {
                    throw new IllegalArgumentException("Title '" + bug.getTitle() + "' already exists");
                }

                Bug existingById = findById(bug.getId());
                if (existingById != null && existingById.getId() != bug.getId()) {
                    throw new IllegalArgumentException("ID '" + bug.getId() + "' already exists");
                }

//                bug.setUpdatedDate(LocalDateTime.now());
                bugsCache.set(i, bug);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // ==================== DELETE ====================

    /**
     * Delete a bug by ID
     * @param id The bug ID to delete
     * @return true if deleted, false if not found
     */
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

    /**
     * Delete all users (use with caution!)
     */
    public void deleteAll() {
        bugsCache.clear();
        saveToFile();
    }


    // ==================== FILE OPERATIONS ====================

    /**
     * Load all bugs from file into cache
     */
    private void loadFromFile() {
        try {
            Scanner scanner = new Scanner(new File(FILE_PATH));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.equals(HEADER))
                    continue;
                Bug bug = parseBugs(line);
                if (bug != null) {
                    bugsCache.add(bug);
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Parse a string line from file into Bug object
     */
    private Bug parseBugs(String line) {
        try {
            String[] dataFields = line.split(DELIMITER);
            if (dataFields[0].equals("id"))
                return null;
            else {
                Bug bug = new Bug();
                bug.setId(Integer.parseInt(dataFields[0]));
                bug.setTitle(dataFields[1]);
                bug.setDescription(dataFields[2]);
                bug.setProjectName(dataFields[3]);
                bug.setStatus(BugStatus.valueOf(dataFields[4]));
                bug.setPriority(BugPriority.valueOf(dataFields[5]);
                bug.setReporterId(dataFields[6]);
                bug.setAssigneeId(Role.valueOf(dataFields[7]));
                bug.setCreatedAt(LocalDateTime.parse(dataFields[8], DATE_FORMAT));
                bug.setUpdatedAt(LocalDateTime.parse(dataFields[9], DATE_FORMAT));
                return bug;
            }
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Save all bugs from cache to file
     */
    private void saveToFile() {
        try {
            PrintWriter printWriter = new PrintWriter(FILE_PATH);
            printWriter.println(HEADER);
            if (!bugsCache.isEmpty()) {
                for (int i = 0; i < bugsCache.size(); i++) {
                    Bug bug = bugsCache.get(i);
                    printWriter.println(formatBug(bug));
                }
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Format a Bug object into a string line for file storage
     */
    private String formatBug(Bug bug) {
        return bug.getId() + DELIMITER
                + bug.getTitle() + DELIMITER
                + bug.getDescription() + DELIMITER
                + bug.getProjectName() + DELIMITER
                + bug.getStatus() + DELIMITER
                + bug.getPriority() + DELIMITER;
        return +bug.getReporter().getId() + DELIMITER
                + bug.getAssignee().getId() + DELIMITER
                + bug.getCreatedAt() + DELIMITER
                + bug.getUpdatedAt();
    }


    /**
     * Create the data file with header
     */
    private void createDataFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.createNewFile()) {       //create file if not exist and return 1(if exist return 0)
                PrintWriter printWriter = new PrintWriter(FILE_PATH);
                printWriter.println(HEADER);
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate the next available ID
     */
    private int generateNextId() {
        int maxId = 0;
        for (Bug bug : bugsCache) {
            if (bug.getId() > maxId) {
                maxId = bug.getId();
            }
        }
        return maxId + 1;
    }

    // ==================== COUNT & EXISTS ====================

    /**
     * Check if title exists
     *
     * @param title The title to check
     * @return true if exists
     */
    public boolean existsByTitle(String title) {
        return findByTitle(title) != null;
    }

    /**
     * Check if bug ID exists
     *
     * @param id The ID to check
     * @return true if exists
     */
    public boolean existsById(int id) {
        return findById(id) != null;
    }

    /**
     * Get total number of bugs
     * @return bugs count
     */
    public int getTotalBugsCount() {
        return bugsCache.size();
    }

    /**
     * Count bugs by status
     * @param status The status to count
     * @return Number of status with the specified role
     */
    public int getBugsCountByStatus(BugStatus status) {
        int count = 0;
        for (Bug bug : bugsCache) {
            if (bug.getStatus() == status) {
                count++;
            }
        }
        return count;
    }

}
