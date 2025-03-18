package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
