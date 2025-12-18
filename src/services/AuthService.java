package services;

import dao.UserDAO;
import models.User;

/**
 * Authentication service - handles login/logout
 */
public class AuthService {

    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) {
        if (username == null || password == null) return null;

        User user = userDAO.findByUsername(username.trim());
        if (user != null && user.getPassword().equals(password)) {
            SessionManager.getInstance().login(user);
            return user;
        }
        return null;
    }

    public void logout() {
        SessionManager.getInstance().logout();
    }

    public User getCurrentUser() {
        return SessionManager.getInstance().getCurrentUser();
    }

    public boolean isLoggedIn() {
        return SessionManager.getInstance().isLoggedIn();
    }
}
