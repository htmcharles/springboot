package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
}
