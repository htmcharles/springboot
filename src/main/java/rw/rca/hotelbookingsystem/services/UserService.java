package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.User;
import java.util.List;

public interface UserService {
    User registerUser(User user);
    String loginUser(User user);
    User getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    List<User> getAllUsers();
    void changePassword(Long id, String oldPassword, String newPassword);
}
