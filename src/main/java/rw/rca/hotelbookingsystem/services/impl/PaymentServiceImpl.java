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
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
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
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByGuest(Integer guestId) {
        // Since we don't have a direct method for this, we'll need to get payments through bookings
        return paymentRepository.findAll().stream()
                .filter(payment -> payment.getBooking().getGuest().getGuestID().equals(guestId))
                .toList();
    }

    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(PaymentStatus.valueOf(status.toUpperCase()));
    }
}
