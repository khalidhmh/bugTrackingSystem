package models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User entity model - Contains ONLY data and basic validation
 * Management operations (CRUD) in UserDAO/UserService
 * @author Team
 */

public class User {
    
    // ==================== Fields ====================
    private int id;
    private String firstName;      
    private String lastName;
    private String username;     
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // ==================== Constructors ====================
    
    /**
     * Default constructor - Required for some frameworks
     */
    public User() {
        setCreatedAt();
        setUpdatedAt();
    }
    
    /**
     * Constructor without ID - For creating NEW users (ID assigned by DAO/Database)
     */
    public User(String firstName, String lastName, String username, 
                        String email, String password, Role role) {
        this();  // Call default constructor for timestamps
        // Id will be set later by DAO/Database
        this.firstName = firstName;
        this.lastName = lastName;
        setUsername(username);  
        setEmail(email);        
        setPassword(password);  
        this.role = role;
    }
    
    /**
     * Full constructor - For loading EXISTING users from database/file
     */
    public User(int id, String firstName, String lastName, String username,
                        String email, String password, Role role, 
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        setUsername(username);  
        setEmail(email);        
        setPassword(password);  
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // ==================== Getters & Setters ====================
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setUpdatedAt();
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
        setUpdatedAt();
    }
    
    /**
     * Get full name (firstName + lastName)
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters");
        }
        this.username = username.trim();
        setUpdatedAt();
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.trim().toLowerCase();
        setUpdatedAt();
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }
        this.password = password;
        setUpdatedAt();
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = role;
        setUpdatedAt();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }   

    private void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
    // ==================== Helper Methods ====================
    
    /**
     * Simple email validation
     */
    private boolean isValidEmail(String email) {
        return email != null 
            && email.contains("@") 
            && email.contains(".")
            && email.indexOf("@") < email.lastIndexOf(".");
    }
    
    /**
     * Check if user has specific role
     */
    public boolean hasRole(Role role) {
        return this.role == role;
    }
    
    /**
     * Check if user is admin
     */
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    // ==================== Object Methods ====================
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + getFullName() + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
