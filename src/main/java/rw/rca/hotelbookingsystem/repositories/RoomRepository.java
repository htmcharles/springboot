package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByType(String type);
    List<Room> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Room> findByStatus(String status);
    List<Room> findByRoomNumber(String roomNumber);
}
