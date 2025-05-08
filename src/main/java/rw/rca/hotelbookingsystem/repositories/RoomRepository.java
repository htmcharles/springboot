/**
 * Repository interface for managing Room entities in the database.
 * Provides methods for basic CRUD operations and custom queries for room filtering.
 */
package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    /**
     * Finds all rooms of a specific type
     * @param type The room type to search for
     * @return List of rooms matching the specified type
     */
    List<Room> findByType(String type);

    /**
     * Finds rooms within a specific price range
     * @param minPrice The minimum price
     * @param maxPrice The maximum price
     * @return List of rooms with prices between minPrice and maxPrice
     */
    List<Room> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Finds rooms with a specific status
     * @param status The room status to search for
     * @return List of rooms with the specified status
     */
    List<Room> findByStatus(String status);

    /**
     * Finds rooms by their room number
     * @param roomNumber The room number to search for
     * @return List of rooms matching the room number
     */
    List<Room> findByRoomNumber(String roomNumber);

    /**
     * Finds rooms with at least the specified capacity
     * @param capacity The minimum required capacity
     * @return List of rooms with capacity greater than or equal to the specified value
     */
    List<Room> findByCapacityGreaterThanEqual(Integer capacity);
}
