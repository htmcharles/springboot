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
import rw.rca.hotelbookingsystem.services.UserService;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.ZoneId;

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

    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> createBooking(@RequestBody Map<String, Object> bookingData) {
        Integer roomId = Integer.valueOf(bookingData.get("roomId").toString());
        Long userId = Long.valueOf(bookingData.get("userId").toString());
        String checkInDate = bookingData.get("checkInDate").toString();
        String checkOutDate = bookingData.get("checkOutDate").toString();
        String additionalRequests = bookingData.get("additionalRequests").toString();

        // Fetch the Room object from the database
        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Create a new Booking object
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(new User());

        // Parse the date strings into LocalDate objects
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(checkInDate, formatter);
        LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);

        // Set the parsed LocalDate objects in the Booking
        booking.setCheckInDate(checkIn);

        // Convert LocalDate to Date
        Date checkOutDateConverted = Date.from(checkOut.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Set the Date object in the Booking
        booking.setCheckOut(checkOutDateConverted);
        booking.setAdditionalRequests(additionalRequests);

        // Calculate the number of days
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);

        // Determine the base price per night based on room type
        double basePrice;
        switch (room.getType().toLowerCase()) {
            case "deluxe":
                basePrice = 100.0; // Example price for Deluxe
                break;
            case "suite":
                basePrice = 150.0; // Example price for Suite
                break;
            case "standard":
                basePrice = 80.0; // Example price for Standard
                break;
            default:
                basePrice = room.getPrice(); // Fallback to room's price if type is unknown
        }

        // Calculate total price
        int numberOfPeople = Integer.parseInt(bookingData.get("numberOfPeople").toString());
        double totalPrice = basePrice * daysBetween * numberOfPeople;

        // Set the total price in the Booking
        booking.setTotalPrice(totalPrice);

        // Fetch the existing User object from the database
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Set the User in the Booking
        booking.setUser(user);

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

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
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
