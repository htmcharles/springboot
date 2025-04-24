package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Address;
import rw.rca.hotelbookingsystem.models.Payment;

import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    interface BookingRepository extends JpaRepository<Address.Booking, Long> {
        List<Address.Booking> findByUserId(Long userId);
        List<Address.Booking> findByRoomId(Long roomId);
    }
}
