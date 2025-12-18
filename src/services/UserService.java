package services;

import dao.UserDAO;
import enums.Role;
import models.User;
import java.util.List;

/**
 * User service - business logic for user management
 */
public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // ==================== CREATE ====================

    public User createUser(String firstName, String lastName, String username,
                          String email, String password, Role role) {
        
        if (userDAO.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userDAO.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(firstName, lastName, username, email, password, role);
        return userDAO.save(user);
    }

    // ==================== READ ====================

    public User getUserById(int id) {
        return userDAO.findById(id);
    }

    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public List<User> getUsersByRole(Role role) {
        return userDAO.findByRole(role);
    }

    public List<User> getAllDevelopers() {
        return userDAO.findByRole(Role.DEVELOPER);
    }

    public List<User> getAllTesters() {
        return userDAO.findByRole(Role.TESTER);
    }

    // ==================== UPDATE ====================

    public boolean updateUser(User user) {
        return userDAO.update(user);
    }

    // ==================== DELETE ====================

    public boolean deleteUser(int userId) {
        User user = userDAO.findById(userId);
        if (user == null) return false;

        // Don't delete last admin
        if (user.getRole() == Role.ADMIN && userDAO.countByRole(Role.ADMIN) <= 1) {
            throw new IllegalStateException("Cannot delete the last admin");
        }
        return userDAO.delete(userId);
    }

    // ==================== STATS ====================

    public int getTotalUserCount() {
        return userDAO.count();
    }

    public int countByRole(Role role) {
        return userDAO.countByRole(role);
    }
}
