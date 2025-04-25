package rw.rca.hotelbookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hotelbookingsystem.models.User;
import rw.rca.hotelbookingsystem.models.UserRole;
import rw.rca.hotelbookingsystem.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Basic validation (can be enhanced)
            if (user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getFirstName() == null || user.getFirstName().isEmpty() ||
                user.getLastName() == null || user.getLastName().isEmpty() ||
                user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty() ||
                user.getAddress() == null) {
                return ResponseEntity.badRequest().body("Missing required fields");
            }

            if (userService.existsByEmail(user.getEmail())) {
                return ResponseEntity.status(409).body("Email already registered!");
            }

            if (user.getRole() == null) {
                user.setRole(UserRole.CUSTOMER);
            }

            User registeredUser = userService.registerUser(user);
            registeredUser.setPassword("********"); // Mask password in response

            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        try {
            System.out.println("Login attempt - Email: " + loginData.getEmail());

            if (loginData.getEmail() == null || loginData.getEmail().isEmpty() ||
                loginData.getPassword() == null || loginData.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Email and password are required");
            }

            // Use the existing UserService.loginUser which handles finding and password check
            User authenticatedUser = userService.loginUser(loginData);
            authenticatedUser.setPassword("********"); // Mask password

            // Simple success response without JWT
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("user", authenticatedUser);
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) { // Catch specific exceptions if needed
            System.err.println("Login error: " + e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            // Provide a generic error message for security
            errorResponse.put("message", "Invalid credentials or user not found");
            return ResponseEntity.status(401).body(errorResponse);
        } catch (Exception e) {
            System.err.println("Unexpected login error: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "An unexpected error occurred during login.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        users.forEach(user -> user.setPassword("********"));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            user.setPassword("********");
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            // Note: This currently doesn't handle password updates securely
            User updatedUser = userService.updateUser(id, user);
            updatedUser.setPassword("********");
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Update failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Delete failed: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable Long id,
            @RequestBody PasswordChangeRequest request) {
        try {
            // Basic validation
            if (request.oldPassword == null || request.oldPassword.isEmpty() ||
                request.newPassword == null || request.newPassword.isEmpty()) {
                return ResponseEntity.badRequest().body("Old and new passwords are required.");
            }
            userService.changePassword(id, request.oldPassword, request.newPassword);
            return ResponseEntity.ok("Password changed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Reset password logic might need significant rework without security context
    // Commenting out for now
    /*
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            User user = userService.getUserByEmail(email);
            String resetToken = UUID.randomUUID().toString();
            // Need a new mechanism to store/send tokens securely
            // passwordResetTokens.put(resetToken, email);
            System.out.println("Password reset token for " + email + ": " + resetToken); // Log for now

            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset token generated (check server logs)");
            response.put("token", resetToken); // Sending token in response for testing
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Email not found");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("password");

        // Need a new mechanism to validate tokens
        // String email = passwordResetTokens.get(token);
        // if (email == null) {
        //     return ResponseEntity.badRequest().body("Invalid or expired reset token");
        // }

        // Dummy implementation - requires secure token handling
        return ResponseEntity.status(501).body("Password reset functionality needs rework.");

        // try {
        //     User user = userService.getUserByEmail(email);
        //     user.setPassword(newPassword); // Password should be hashed here!
        //     userService.updateUser(user.getId(), user);
        //     // passwordResetTokens.remove(token);
        //     return ResponseEntity.ok("Password has been reset successfully");
        // } catch (RuntimeException e) {
        //     return ResponseEntity.status(404).body("User not found");
        // }
    }

    @GetMapping("/validate-reset-token/{token}")
    public ResponseEntity<?> validateResetToken(@PathVariable String token) {
         // Need a new mechanism to validate tokens
        // String email = passwordResetTokens.get(token);
        // if (email != null) {
        //     return ResponseEntity.ok("Valid token");
        // } else {
        //     return ResponseEntity.badRequest().body("Invalid or expired token");
        // }
        return ResponseEntity.status(501).body("Token validation needs rework.");
    }
    */

    // Keep PasswordChangeRequest inner class if needed
    private static class PasswordChangeRequest {
        public String oldPassword;
        public String newPassword;
    }
}
