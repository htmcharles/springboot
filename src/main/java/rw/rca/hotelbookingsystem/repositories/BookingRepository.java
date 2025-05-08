/**
 * Repository interface for managing Booking entities in the database.
 * Provides methods for basic CRUD operations and custom queries for booking management.
 */
package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Booking;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    /**
     * Finds all bookings for a specific room
     * @param roomId The ID of the room to find bookings for
     * @return List of bookings associated with the specified room
     */
    List<Booking> findByRoomId(Integer roomId);

    /**
     * Finds all bookings made by a specific user
     * @param userId The ID of the user to find bookings for
     * @return List of bookings made by the specified user
     */
    List<Booking> findByUser_Id(Long userId);
}
