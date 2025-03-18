package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
