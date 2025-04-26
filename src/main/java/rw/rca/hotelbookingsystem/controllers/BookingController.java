package rw.rca.hotelbookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hotelbookingsystem.models.Booking;
import rw.rca.hotelbookingsystem.services.BookingService;
import rw.rca.hotelbookingsystem.models.Review;
import rw.rca.hotelbookingsystem.services.ReviewService;
import rw.rca.hotelbookingsystem.models.Room;
import rw.rca.hotelbookingsystem.models.User;
import rw.rca.hotelbookingsystem.services.RoomService;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RoomService roomService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @PutMapping(value = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> cancelBooking(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @PutMapping(value = "/{id}/check-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> checkIn(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.checkIn(id));
    }

    @PutMapping(value = "/{id}/check-out", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> checkOut(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.checkOut(id));
    }

    @GetMapping(value = "/room/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> getRoomBookings(@PathVariable Integer roomId) {
        return ResponseEntity.ok(bookingService.getRoomBookings(roomId));
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String, Object> reviewData) {
        Integer roomId = Integer.valueOf(reviewData.get("roomId").toString());
        Long userId = Long.valueOf(reviewData.get("userId").toString());
        Integer rating = Integer.valueOf(reviewData.get("rating").toString());
        String comment = reviewData.get("comment").toString();

        // Fetch the Room object from the database
        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }

        // Create a new Review object
        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setRoom(room);

        // Set the User object with the userId
        User user = new User();
        user.setId(userId);
        review.setUser(user);

        return ResponseEntity.ok(reviewService.createReview(review));
    }
}
