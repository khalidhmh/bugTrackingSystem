package dao;

import models.User;
import config.AppConfig;
import enums.Role;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Data Access Object for User file operations
 * Handles all CRUD operations for users stored in text file
 * File format: id|firstName|lastName|username|email|password|role|createdAt|updatedAt
 * 
 * @author Team
 */
public class UserDAO {
    
    private static final String FILE_PATH = "data/users.txt";
    private static final String DELIMITER_REGEX = "\\|";  // For splitting
    private static final String DELIMITER = "|";           // For writing
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final String HEADER = "id" + DELIMITER
                                        + "firstName" + DELIMITER
                                        + "lastName" + DELIMITER
                                        + "username" + DELIMITER
                                        + "email" + DELIMITER
                                        + "password" + DELIMITER
                                        + "role" + DELIMITER
                                        + "createdAt" + DELIMITER
                                        + "updatedAt";
    
    private static List<User> usersCache = new ArrayList<>();
    
    // ==================== Constructor ====================
    
    public UserDAO() {
        loadFromFile();
        
        // Create default admin if no users exist
        if (usersCache.isEmpty()) {
            createDefaultAdmin();
        }
    }
    
    // ==================== CREATE ====================
    
    /**
     * Save a new user to the file
     * @param user The user to save (ID will be auto-generated)
     * @return The saved user with generated ID
     */
    public User save(User user) {
        // Validate unique constraints
        if (existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username '" + user.getUsername() + "' already exists");
        }
        if (existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' already exists");
        }
        
        // Generate ID and set timestamps
        user.setId(generateNextId());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        // Add to cache and save
        usersCache.add(user);
        saveToFile();

        return user;
    }
    
    // ==================== READ ====================
    
    /**
     * Find user by ID
     * @param id The user ID
     * @return User if found, null otherwise
     */
    public User findById(int id) {
        for (User user : usersCache) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Find user by username
     * @param username The username to search
     * @return User if found, null otherwise
     */
    public User findByUsername(String username) {
        for (User user : usersCache) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Find user by email
     * @param email The email to search
     * @return User if found, null otherwise
     */
    public User findByEmail(String email) {
        for (User user : usersCache) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Find user by username and password (for login)
     * @param username The username
     * @param password The password
     * @return User if credentials match, null otherwise
     */
    public User findByUsernameAndPassword(String username, String password) {
        User user = findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    /**
     * Get all users
     * @return List of all users
     */
    public List<User> findAll() {
        return new ArrayList<>(usersCache);
    }
    
    /**
     * Find all users with a specific role
     * @param role The role to filter by
     * @return List of users with the specified role
     */
    public List<User> findByRole(Role role) {
        List<User> result = new ArrayList<>();
        for (User user : usersCache) {
            if (user.getRole() == role) {
                result.add(user);
            }
        }
        return result;
    }
    
    /**
     * Search users by name, username, or email
     * @param searchTerm The search term
     * @return List of matching users
     */
    public List<User> search(String searchTerm) {
        List<User> result = new ArrayList<>();
        String term = searchTerm.toLowerCase();
        
        for (User user : usersCache) {
            if (user.getFirstName().toLowerCase().contains(term) ||
                user.getLastName().toLowerCase().contains(term) ||
                user.getUsername().toLowerCase().contains(term) ||
                user.getEmail().toLowerCase().contains(term)) {
                result.add(user);
            }
        }
        return result;
    }
    
    // ==================== UPDATE ====================
    
    /**
     * Update an existing user
     * @param user The user with updated data
     * @return true if updated, false if user not found
     */
    public boolean update(User user) {
        for (int i = 0; i < usersCache.size(); i++) {
            if (usersCache.get(i).getId() == user.getId()) {
                // Check unique constraints (excluding current user)
                User existingByUsername = findByUsername(user.getUsername());
                if (existingByUsername != null && existingByUsername.getId() != user.getId()) {
                    throw new IllegalArgumentException("Username '" + user.getUsername() + "' already exists");
                }
                
                User existingByEmail = findByEmail(user.getEmail());
                if (existingByEmail != null && existingByEmail.getId() != user.getId()) {
                    throw new IllegalArgumentException("Email '" + user.getEmail() + "' already exists");
                }
                
                user.setUpdatedAt(LocalDateTime.now());
                usersCache.set(i, user);
                saveToFile();
                return true;
            }
        }
        return false;
    }
    
    // ==================== DELETE ====================
    
    /**
     * Delete a user by ID
     * @param id The user ID to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(int id) {
        for (int i = 0; i < usersCache.size(); i++) {
            if (usersCache.get(i).getId() == id) {
                usersCache.remove(i);
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
        usersCache.clear();
        saveToFile();
    }
    
    // ==================== COUNT & EXISTS ====================
    
    /**
     * Get total number of users
     * @return User count
     */
    public int count() {
        return usersCache.size();
    }
    
    /**
     * Count users by role
     * @param role The role to count
     * @return Number of users with the specified role
     */
    public int countByRole(Role role) {
        int count = 0;
        for (User user : usersCache) {
            if (user.getRole() == role) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Check if username exists
     * @param username The username to check
     * @return true if exists
     */
    public boolean existsByUsername(String username) {
        return findByUsername(username) != null;
    }
    
    /**
     * Check if email exists
     * @param email The email to check
     * @return true if exists
     */
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }
    
    /**
     * Check if user ID exists
     * @param id The ID to check
     * @return true if exists
     */
    public boolean existsById(int id) {
        return findById(id) != null;
    }
    
    // ==================== FILE OPERATIONS ====================
    
    /**
     * Load all users from file into cache
     */
    private void loadFromFile() {
        try {
            Scanner scanner = new Scanner(new File(FILE_PATH));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.equals(HEADER))
                    continue;
                User user = parseUser(line);
                if (user != null) {
                    usersCache.add(user);
                }

            }
            scanner.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //✅  will be handeld by Belal
    }
    
    /**
     * Save all users from cache to file
     */
    private void saveToFile() {
        try {
            PrintWriter printWriter = new PrintWriter(FILE_PATH);
            printWriter.println(HEADER);
            if (!usersCache.isEmpty()){
                for (int i = 0; i < usersCache.size(); i++) {
                    User user = usersCache.get(i);
                    printWriter.println(formatUser(user));
                }
            }
            printWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
       //✅  will be handeld by Belal
    }
    
    /**
     * Parse a string line from file into User object
     */
    private User parseUser(String line) {
        try {
            String[] dataFields = line.split(DELIMITER_REGEX);
            if (dataFields[0].equals("id"))
                return null;
            else {
                User user = new User();
                user.setId(Integer.parseInt(dataFields[0]));
                user.setFirstName(dataFields[1]);
                user.setLastName(dataFields[2]);
                user.setUsername(dataFields[3]);
                user.setEmail(dataFields[4]);
                user.setPassword(dataFields[5]);
                user.setRole(Role.valueOf(dataFields[6]));
                user.setCreatedAt(LocalDateTime.parse(dataFields[7], DATE_FORMAT));
                user.setUpdatedAt(LocalDateTime.parse(dataFields[8], DATE_FORMAT));
                return user;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Format a User object into a string line for file storage
     */
    private String formatUser(User user) {
        return user.getId() + DELIMITER
                + user.getFirstName() + DELIMITER
                + user.getLastName() + DELIMITER
                + user.getUsername() + DELIMITER
                + user.getEmail() + DELIMITER
                + user.getPassword() + DELIMITER
                + user.getRole() + DELIMITER
                + user.getCreatedAt() + DELIMITER
                + user.getUpdatedAt();
        //✅  will be handeld by Belal
    }
    
    /**
     * Create the data file with header
     */
    private void createDataFile() {
        try{
            File file = new File(FILE_PATH);
            if (file.createNewFile()){       //create file if not exist and return 1(if exist return 0)
                PrintWriter printWriter = new PrintWriter(FILE_PATH);
                printWriter.println(HEADER);
                printWriter.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        //✅  will be handeld by Belal
    }

    /**
     * Generate the next available ID
     */
    private int generateNextId() {
        int maxId = 0;
        for (User user : usersCache) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1;
    }
    
    /**
     * Create default admin user if no users exist
     */
    private void createDefaultAdmin() {
        User admin = new User();
        admin.setId(1);
        admin.setFirstName("System");
        admin.setLastName("Admin");
        admin.setUsername("admin");
        admin.setEmail("admin@system.com");
        admin.setPassword("admin123");
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        
        usersCache.add(admin);
        saveToFile();
        
        System.out.println("Default admin created - Username: admin, Password: admin123");
    }
    
    /**
     * Reload data from file (refresh cache)
     */
    public void refresh() {
        usersCache.clear();
        loadFromFile();
    }
    
    // ==================== ALIAS METHODS ====================
    
    /**
     * Get users by role (alias for findByRole)
     */
    public List<User> getUsersByRole(Role role) {
        return findByRole(role);
    }
    
    /**
     * Get user by email (alias for findByEmail)
     */
    public User getUserByEmail(String email) {
        return findByEmail(email);
    }
    
    /**
     * Get user by ID (alias for findById)
     */
    public User getUserById(int id) {
        return findById(id);
    }
}
