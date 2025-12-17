package services;

/**
 * Authentication service for login/logout
 * @author Team
 */

import dao.UserDAO;
import models.User;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) {

        if (username == null || password == null) {
            return null;
        }

        User user = userDAO.findByUsername(username.trim());

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null; // login failed
    }
}
