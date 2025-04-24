package rw.rca.hotelbookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hotelbookingsystem.models.Address;
import rw.rca.hotelbookingsystem.models.User;
import rw.rca.hotelbookingsystem.services.BookingService;
import rw.rca.hotelbookingsystem.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Address.Booking> createBooking(@RequestBody Address.Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address.Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address.Booking>> getUserBookings(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Address.Booking> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @PutMapping("/{id}/check-in")
    public ResponseEntity<Address.Booking> checkIn(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.checkIn(id));
    }

    @PutMapping("/{id}/check-out")
    public ResponseEntity<Address.Booking> checkOut(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.checkOut(id));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Address.Booking>> getRoomBookings(@PathVariable Long roomId) {
        return ResponseEntity.ok(bookingService.getRoomBookings(roomId));
    }

    @RestController
    @RequestMapping("/api/users")
    public static class UserController {

        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public ResponseEntity<User> registerUser(@RequestBody User user) {
            return ResponseEntity.ok(userService.registerUser(user));
        }

        @PostMapping("/login")
        public ResponseEntity<String> loginUser(@RequestBody User user) {
            return ResponseEntity.ok(userService.loginUser(user));
        }

        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Long id) {
            return ResponseEntity.ok(userService.getUserById(id));
        }

        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
            return ResponseEntity.ok(userService.updateUser(id, user));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }

        @GetMapping
        public ResponseEntity<List<User>> getAllUsers() {
            return ResponseEntity.ok(userService.getAllUsers());
        }

        @PutMapping("/{id}/change-password")
        public ResponseEntity<Void> changePassword(
                @PathVariable Long id,
                @RequestParam String oldPassword,
                @RequestParam String newPassword) {
            userService.changePassword(id, oldPassword, newPassword);
            return ResponseEntity.ok().build();
        }
    }
}
