package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Payment;
import rw.rca.hotelbookingsystem.models.PaymentStatus;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByBooking_BookingID(Integer bookingId);
    List<Payment> findByStatus(PaymentStatus status);
}
