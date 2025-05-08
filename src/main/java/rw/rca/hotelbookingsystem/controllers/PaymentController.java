/**
 * REST Controller for handling payment-related HTTP requests.
 * Provides endpoints for processing payments, refunds, and payment management.
 */
package rw.rca.hotelbookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hotelbookingsystem.models.Payment;
import rw.rca.hotelbookingsystem.models.Booking;
import rw.rca.hotelbookingsystem.services.PaymentService;
import rw.rca.hotelbookingsystem.services.BookingService;
import rw.rca.hotelbookingsystem.models.PaymentStatus;
import rw.rca.hotelbookingsystem.models.Room;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    /**
     * Processes a new payment for a booking
     * @param paymentRequest Map containing payment details (bookingId, amount, paymentMethod)
     * @return ResponseEntity containing the processed payment or error message
     */
    @PostMapping
    public ResponseEntity<?> processPayment(@RequestBody Map<String, Object> paymentRequest) {
        try {
            // Extract booking ID from the request
            Integer bookingId = Integer.valueOf(paymentRequest.get("bookingId").toString());

            // Get the booking
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                return ResponseEntity.badRequest().body("Booking not found");
            }

            // Create payment object
            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setAmount(Double.valueOf(paymentRequest.get("amount").toString()));
            payment.setPaymentMethod(paymentRequest.get("paymentMethod").toString());
            payment.setStatus(PaymentStatus.PENDING);

            // Process the payment
            Payment processedPayment = paymentService.processPayment(payment);
            return ResponseEntity.ok(processedPayment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing payment: " + e.getMessage());
        }
    }

    /**
     * Retrieves a specific payment by its ID
     * @param id The ID of the payment to retrieve
     * @return ResponseEntity containing the payment
     */
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    /**
     * Retrieves all payments for a specific booking
     * @param bookingId The ID of the booking to get payments for
     * @return ResponseEntity containing list of payments for the booking
     */
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Payment>> getPaymentsByBooking(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBooking(bookingId));
    }

    /**
     * Processes a refund for a booking's most recent payment
     * @param bookingId The ID of the booking to process refund for
     * @return ResponseEntity containing the refunded payment or error message
     */
    @PostMapping("/booking/{bookingId}/refund")
    public ResponseEntity<?> processRefund(@PathVariable Integer bookingId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByBooking(bookingId);
            if (payments.isEmpty()) {
                return ResponseEntity.badRequest().body("No payments found for booking ID: " + bookingId);
            }
            // Get the most recent payment for this booking
            Payment payment = payments.get(payments.size() - 1);
            return ResponseEntity.ok(paymentService.processRefund(payment.getPaymentID()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing refund: " + e.getMessage());
        }
    }

    /**
     * Retrieves all payments with a specific status
     * @param status The payment status to search for
     * @return ResponseEntity containing list of payments with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    /**
     * Retrieves all payments in the system
     * @return ResponseEntity containing list of all payments
     */
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    /**
     * Retrieves all payments made by a specific user
     * @param userId The ID of the user to get payments for
     * @return ResponseEntity containing list of user's payments or error message
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPaymentsByUser(@PathVariable Long userId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByUser(userId);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving payments: " + e.getMessage());
        }
    }

    /**
     * Retrieves room details associated with a payment
     * @param id The ID of the payment to get room details for
     * @return ResponseEntity containing the room details or error message
     */
    @GetMapping("/{id}/room")
    public ResponseEntity<?> getRoomDetailsByPaymentId(@PathVariable Integer id) {
        try {
            Payment payment = paymentService.getPaymentById(id);
            if (payment.getBooking() == null) {
                return ResponseEntity.badRequest().body("No booking associated with this payment");
            }
            Room room = payment.getBooking().getRoom();
            if (room == null) {
                return ResponseEntity.badRequest().body("No room associated with this payment's booking");
            }
            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving room details: " + e.getMessage());
        }
    }

    /**
     * Updates an existing payment's information
     * @param id The ID of the payment to update
     * @param updateRequest Map containing updated payment details (amount, paymentMethod, status)
     * @return ResponseEntity containing the updated payment or error message
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable Integer id, @RequestBody Map<String, Object> updateRequest) {
        try {
            Payment existingPayment = paymentService.getPaymentById(id);

            if (updateRequest.containsKey("amount")) {
                existingPayment.setAmount(Double.valueOf(updateRequest.get("amount").toString()));
            }
            if (updateRequest.containsKey("paymentMethod")) {
                existingPayment.setPaymentMethod(updateRequest.get("paymentMethod").toString());
            }
            if (updateRequest.containsKey("status")) {
                existingPayment.setStatus(PaymentStatus.valueOf(updateRequest.get("status").toString().toUpperCase()));
            }

            Payment updatedPayment = paymentService.updatePayment(existingPayment);
            return ResponseEntity.ok(updatedPayment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating payment: " + e.getMessage());
        }
    }
}
