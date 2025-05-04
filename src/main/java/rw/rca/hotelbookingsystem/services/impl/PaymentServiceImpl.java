package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.Payment;
import rw.rca.hotelbookingsystem.models.PaymentStatus;
import rw.rca.hotelbookingsystem.repositories.PaymentRepository;
import rw.rca.hotelbookingsystem.services.PaymentService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment processPayment(Payment payment) {
        if (payment.getBooking() == null) {
            throw new IllegalArgumentException("Booking reference is required for payment processing");
        }

        if (payment.getAmount() == null || payment.getAmount() <= 0) {
            throw new IllegalArgumentException("Valid payment amount is required");
        }

        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method is required");
        }

        // Set payment date and keep the PENDING status
        payment.setPaymentDate(LocalDateTime.now());

        try {
            // Save the payment with PENDING status
            return paymentRepository.save(payment);
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new RuntimeException("Payment processing failed: " + e.getMessage());
        }
    }

    @Override
    public Payment getPaymentById(Integer id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public List<Payment> getPaymentsByBooking(Integer bookingId) {
        return paymentRepository.findByBooking_BookingID(bookingId);
    }

    @Override
    public Payment processRefund(Integer id) {
        Payment payment = getPaymentById(id);
        payment.setStatus(PaymentStatus.PARTIALLY_REFUNDED);
        payment.setRefundDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(PaymentStatus.valueOf(status.toUpperCase()));
    }

    @Override
    public List<Payment> getPaymentsByUser(Integer userId) {
        // Retrieve payments by filtering through bookings associated with the user
        return paymentRepository.findAll().stream()
                .filter(payment -> payment.getBooking().getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment updatePayment(Payment payment) {
        if (payment.getBooking() == null) {
            throw new IllegalArgumentException("Booking reference cannot be null");
        }

        if (payment.getAmount() == null || payment.getAmount() <= 0) {
            throw new IllegalArgumentException("Valid payment amount is required");
        }

        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method is required");
        }

        return paymentRepository.save(payment);
    }
}
