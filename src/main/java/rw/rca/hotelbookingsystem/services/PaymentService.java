package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Address;

import java.util.List;

public interface PaymentService {
    Address.Payment processPayment(Address.Payment payment);
    Address.Payment getPaymentById(Long id);
    List<Address.Payment> getPaymentsByBooking(Long bookingId);
    Address.Payment processRefund(Long id);
    List<Address.Payment> getUserPayments(Long userId);
    List<Address.Payment> getPaymentsByStatus(String status);
}
