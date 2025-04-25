package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.User;
import rw.rca.hotelbookingsystem.repositories.UserRepository;
import rw.rca.hotelbookingsystem.services.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        try {
            // Validate user data
            if (user == null) {
                throw new IllegalArgumentException("User cannot be null");
            }

            // Log the registration attempt
            System.out.println("Attempting to register user with email: " + user.getEmail());

            // Save user - password should already be plain text if registered this way
            User savedUser = userRepository.save(user);

            // Log successful registration
            System.out.println("Successfully registered user with ID: " + savedUser.getId());

            return savedUser;
        } catch (Exception e) {
            // Log the error
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to register user: " + e.getMessage());
        }
    }

    @Override
    public User loginUser(User user) {
        try {
            // Validate input
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                throw new IllegalArgumentException("Email is required");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password is required");
            }

            // Find user by email
            User existingUser = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + user.getEmail()));

            // Direct password comparison
            if (!user.getPassword().equals(existingUser.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            // Log successful login
            System.out.println("User logged in successfully: " + existingUser.getEmail());

            return existingUser;
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAddress(user.getAddress());
        // Important: Password update should happen via changePassword
        // If allowing password change here, it MUST be hashed.
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = getUserById(id);
        // Direct password comparison
        if (!oldPassword.equals(user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        // Set new password as plain text (consider hashing for real applications)
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
