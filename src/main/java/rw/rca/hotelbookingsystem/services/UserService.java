/**
 * Service interface for managing user-related business logic.
 * Provides methods for user registration, authentication, and profile management.
 */
package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.User;
import rw.rca.hotelbookingsystem.models.UserRole;

import java.util.List;

public interface UserService {
    /**
     * Registers a new user in the system
     * @param user The user object containing registration details
     * @return The registered user with generated ID
     */
    User registerUser(User user);

    /**
     * Authenticates a user and creates a session
     * @param user The user object containing login credentials
     * @return The authenticated user if credentials are valid
     */
    User loginUser(User user);

    /**
     * Retrieves all users in the system
     * @return List of all users
     */
    List<User> getAllUsers();

    /**
     * Retrieves a specific user by their ID
     * @param id The ID of the user to retrieve
     * @return The user with the specified ID
     */
    User getUserById(Long id);

    /**
     * Updates a user's information
     * @param id The ID of the user to update
     * @param user The user object containing updated information
     * @return The updated user
     */
    User updateUser(Long id, User user);

    /**
     * Deletes a user from the system
     * @param id The ID of the user to delete
     */
    void deleteUser(Long id);

    /**
     * Retrieves a user by their email address
     * @param email The email address to search for
     * @return The user with the specified email
     */
    User getUserByEmail(String email);

    /**
     * Changes a user's password
     * @param id The ID of the user
     * @param oldPassword The current password for verification
     * @param newPassword The new password to set
     */
    void changePassword(Long id, String oldPassword, String newPassword);

    /**
     * Checks if a user exists with the given email
     * @param email The email to check
     * @return true if a user exists with the email, false otherwise
     */
    boolean existsByEmail(String email);
}
