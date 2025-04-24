package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.Address;
import rw.rca.hotelbookingsystem.services.PaymentService;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Address.Payment processPayment(Address.Payment payment) {
        // TODO: Implement payment processing logic
        return payment;
    }

    @Override
    public Address.Payment getPaymentById(Long id) {
        // TODO: Implement get payment by ID logic
        return null;
    }

    @Override
    public List<Address.Payment> getPaymentsByBooking(Long bookingId) {
        // TODO: Implement get payments by booking logic
        return null;
    }

    @Override
    public Address.Payment processRefund(Long id) {
        // TODO: Implement refund processing logic
        return null;
    }

    @Override
    public List<Address.Payment> getUserPayments(Long userId) {
        // TODO: Implement get user payments logic
        return null;
    }

    @Override
    public List<Address.Payment> getPaymentsByStatus(String status) {
        // TODO: Implement get payments by status logic
        return null;
    }
}
