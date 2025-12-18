package services;

import models.User;

/**
 * Simple session manager - stores current logged in user
 */
public class SessionManager {

    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
    }

    public void logout() {
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Static convenience method to get the current user
     */
    public static User getLoggedInUser() {
        return getInstance().getCurrentUser();
    }

    public int getCurrentUserId() {
        return currentUser != null ? currentUser.getId() : -1;
    }
}
