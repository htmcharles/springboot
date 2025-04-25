package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Payment;
import java.util.List;

public interface PaymentService {
    Payment processPayment(Payment payment);
    Payment getPaymentById(Integer id);
    List<Payment> getPaymentsByBooking(Integer bookingId);
    Payment processRefund(Integer id);
    List<Payment> getPaymentsByGuest(Integer guestId);
    List<Payment> getPaymentsByStatus(String status);
}
