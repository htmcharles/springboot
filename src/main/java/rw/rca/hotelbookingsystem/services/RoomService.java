/**
 * Service interface for managing room-related business logic.
 * Provides methods for room management, availability checking, and search functionality.
 */
package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Room;
import java.util.List;

public interface RoomService {
    /**
     * Retrieves all rooms in the system
     * @return List of all rooms
     */
    List<Room> getAllRooms();

    /**
     * Retrieves a specific room by its ID
     * @param id The ID of the room to retrieve
     * @return The room with the specified ID
     */
    Room getRoomById(Integer id);

    /**
     * Creates a new room in the system
     * @param room The room object containing room details
     * @return The created room with generated ID
     */
    Room createRoom(Room room);

    /**
     * Updates a room's information
     * @param id The ID of the room to update
     * @param room The room object containing updated information
     * @return The updated room
     */
    Room updateRoom(Integer id, Room room);

    /**
     * Deletes a room from the system
     * @param id The ID of the room to delete
     */
    void deleteRoom(Integer id);

    /**
     * Searches for rooms based on multiple criteria
     * @param type The type of room
     * @param minPrice Minimum price range
     * @param maxPrice Maximum price range
     * @param capacity Minimum required capacity
     * @return List of rooms matching the search criteria
     */
    List<Room> searchRooms(String type, Double minPrice, Double maxPrice, Integer capacity);

    /**
     * Gets available rooms for a specific date range
     * @param checkInDate Check-in date in string format
     * @param checkOutDate Check-out date in string format
     * @return List of available rooms for the specified period
     */
    List<Room> getAvailableRooms(String checkInDate, String checkOutDate);

    /**
     * Gets all currently available rooms
     * @return List of rooms with AVAILABLE status
     */
    List<Room> getAvailableRooms();

    /**
     * Gets rooms of a specific type
     * @param type The room type to search for
     * @return List of rooms of the specified type
     */
    List<Room> getRoomsByType(String type);

    /**
     * Gets rooms within a specific price range
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of rooms within the price range
     */
    List<Room> getRoomsByPriceRange(Double minPrice, Double maxPrice);

    /**
     * Gets rooms with at least the specified capacity
     * @param capacity Minimum required capacity
     * @return List of rooms meeting the capacity requirement
     */
    List<Room> getRoomsByCapacity(Integer capacity);

    /**
     * Updates the status of a room
     * @param id The ID of the room to update
     * @param status The new status to set
     * @return The updated room
     */
    Room updateRoomStatus(Integer id, String status);
}
