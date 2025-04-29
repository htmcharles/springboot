package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.Room;
import rw.rca.hotelbookingsystem.models.Staff;
import rw.rca.hotelbookingsystem.repositories.RoomRepository;
import rw.rca.hotelbookingsystem.services.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Integer id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Integer id, Room room) {
        Room existingRoom = getRoomById(id);
        existingRoom.setRoomNumber(room.getRoomNumber());
        existingRoom.setType(room.getType());
        existingRoom.setPrice(room.getPrice());
        existingRoom.setStatus(room.getStatus());
        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Integer id) {
        // Get the room first
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + id));

        // Remove all staff assignments for this room
        if (room.getAssignedStaff() != null && !room.getAssignedStaff().isEmpty()) {
            // Create a new set to avoid ConcurrentModificationException
            Set<Staff> staffToRemove = new HashSet<>(room.getAssignedStaff());

            // Remove this room from each staff member's assigned rooms
            for (Staff staff : staffToRemove) {
                staff.getAssignedRooms().remove(room);
                room.getAssignedStaff().remove(staff);
            }

            // Save the room to update the assignments
            roomRepository.save(room);
        }

        // Now it's safe to delete the room
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> searchRooms(String type, Double minPrice, Double maxPrice, Integer capacity) {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .filter(room -> (type == null || room.getType().equals(type)))
                .filter(room -> (minPrice == null || room.getPrice() >= minPrice))
                .filter(room -> (maxPrice == null || room.getPrice() <= maxPrice))
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> getAvailableRooms(String checkInDate, String checkOutDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(checkInDate, formatter);
        LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);

        return roomRepository.findAll().stream()
                .filter(room -> room.getStatus().equals("AVAILABLE"))
                .collect(Collectors.toList());
    }
}
