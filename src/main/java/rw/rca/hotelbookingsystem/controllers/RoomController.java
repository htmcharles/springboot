/**
 * REST Controller for handling room-related HTTP requests.
 * Provides endpoints for room management, searching, and availability checking.
 */
package rw.rca.hotelbookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hotelbookingsystem.models.Room;
import rw.rca.hotelbookingsystem.services.RoomService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * Retrieves all rooms in the system
     * @return ResponseEntity containing list of all rooms
     */
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    /**
     * Retrieves a specific room by its ID
     * @param id The ID of the room to retrieve
     * @return ResponseEntity containing the room
     */
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    /**
     * Creates a new room in the system
     * @param room The room object containing room details
     * @return ResponseEntity containing the created room
     */
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return ResponseEntity.ok(roomService.createRoom(room));
    }

    /**
     * Updates a room's information
     * @param id The ID of the room to update
     * @param room The room object containing updated information
     * @return ResponseEntity containing the updated room
     */
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Integer id, @RequestBody Room room) {
        return ResponseEntity.ok(roomService.updateRoom(id, room));
    }

    /**
     * Deletes a room from the system
     * @param id The ID of the room to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates the status of a room
     * @param id The ID of the room to update
     * @param statusUpdate Map containing the new status
     * @return ResponseEntity containing the updated room or error message
     */
    @PostMapping("/{id}/status")
    public ResponseEntity<?> updateRoomStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> statusUpdate) {
        try {
            String newStatus = statusUpdate.get("status");
            if (newStatus == null) {
                return ResponseEntity
                    .badRequest()
                    .body("Status field is required in request body");
            }
            Room updatedRoom = roomService.updateRoomStatus(id, newStatus);
            return ResponseEntity.ok(updatedRoom);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .badRequest()
                .body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Room not found with ID: " + id);
        }
    }

    /**
     * Searches for rooms based on multiple criteria
     * @param type The type of room (optional)
     * @param minPrice Minimum price range (optional)
     * @param maxPrice Maximum price range (optional)
     * @param capacity Minimum required capacity (optional)
     * @return ResponseEntity containing list of rooms matching the criteria
     */
    @GetMapping("/search")
    public ResponseEntity<List<Room>> searchRooms(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer capacity) {
        return ResponseEntity.ok(roomService.searchRooms(type, minPrice, maxPrice, capacity));
    }

    /**
     * Gets available rooms for a specific date range
     * @param checkInDate Check-in date in string format
     * @param checkOutDate Check-out date in string format
     * @return ResponseEntity containing list of available rooms
     */
    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate) {
        return ResponseEntity.ok(roomService.getAvailableRooms(checkInDate, checkOutDate));
    }
}
