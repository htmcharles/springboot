package rw.rca.hotelbookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hotelbookingsystem.models.Payment;
import rw.rca.hotelbookingsystem.models.Booking;
import rw.rca.hotelbookingsystem.services.PaymentService;
import rw.rca.hotelbookingsystem.services.BookingService;
import rw.rca.hotelbookingsystem.models.PaymentStatus;

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

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Payment>> getPaymentsByBooking(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBooking(bookingId));
    }

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

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

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
