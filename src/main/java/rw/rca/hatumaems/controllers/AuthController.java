/**
 * REST Controller for handling user authentication requests.
 * Provides endpoints for user registration and login.
 */
package rw.rca.hatumaems.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hatumaems.models.User;
import rw.rca.hatumaems.services.UserService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * Simple endpoint to test if the auth server is running
     * @return A hello world message
     */
    @GetMapping("/hello")
    public String hello() {
        return "Auth server is running!";
    }

    /**
     * Handles user registration requests
     * @param user The user object containing registration details
     * @return ResponseEntity containing the registered user or error message
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Basic validation
            if (user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getFirstName() == null || user.getFirstName().isEmpty() ||
                user.getLastName() == null || user.getLastName().isEmpty() ||
                user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty() ||
                user.getDepartment() == null || user.getDepartment().isEmpty()) {
                return ResponseEntity.badRequest().body("Missing required fields");
            }

            // Set full name from first and last name
            user.setFullName(user.getFirstName() + " " + user.getLastName());

            // Check if email already exists
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.status(409).body("Email already registered!");
            }

            // Create the user
            User registeredUser = userService.createUser(user);

            // Create response object
            Map<String, Object> response = new HashMap<>();
            response.put("id", registeredUser.getId());
            response.put("email", registeredUser.getEmail());
            response.put("fullName", registeredUser.getFullName());
            response.put("userType", registeredUser.getUserType());
            response.put("message", "Registration successful");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Handles user login requests
     * @param credentials The map containing login credentials (email and password)
     * @return ResponseEntity containing the authenticated user or error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            System.out.println("Login attempt - Email: " + email);

            if (email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {
                return ResponseEntity.badRequest().body("Email and password are required");
            }

            if (userService.authenticate(email, password)) {
                User user = userService.findByEmail(email).get();

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("id", user.getId());
                response.put("email", user.getEmail());
                response.put("fullName", user.getFullName());
                response.put("userType", user.getUserType());
                response.put("message", "Login successful");

                return ResponseEntity.ok(response);
            }

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(errorResponse);

        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "An unexpected error occurred during login");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
