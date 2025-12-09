package utils;

import models.User;

/**
 * Manages the current logged-in user session
 * @author Team
 */
public class SessionManager {
    
    private static User currentUser;
    
    /**
     * Get the current logged-in user
     * @return the current user or null if no user is logged in
     */
    public static User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Set the current logged-in user
     * @param user the user to set as current
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    
    /**
     * Clear the current session (logout)
     */
    public static void clearSession() {
        currentUser = null;
    }
    
    /**
     * Check if a user is currently logged in
     * @return true if a user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
