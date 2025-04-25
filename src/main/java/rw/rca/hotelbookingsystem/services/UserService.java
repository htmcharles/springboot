package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.User;
import rw.rca.hotelbookingsystem.models.UserRole;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    User loginUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getUserByEmail(String email);
    void changePassword(Long id, String oldPassword, String newPassword);
    boolean existsByEmail(String email);
}
