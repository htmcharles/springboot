package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.Booking;
import rw.rca.hotelbookingsystem.models.Room;
import rw.rca.hotelbookingsystem.models.Staff;
import rw.rca.hotelbookingsystem.repositories.BookingRepository;
import rw.rca.hotelbookingsystem.repositories.RoomRepository;
import rw.rca.hotelbookingsystem.services.ReviewService;
import rw.rca.hotelbookingsystem.services.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookingRepository bookingRepository;

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
        existingRoom.setCapacity(room.getCapacity());
        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Integer id) {
        Room room = getRoomById(id);

        // Delete all reviews associated with this room
        reviewService.getRoomReviews(id.longValue()).forEach(reviewMap -> {
            Long reviewId = ((Number) reviewMap.get("id")).longValue();
            reviewService.deleteReview(reviewId);
        });

        // Mark all bookings for this room as CANCELLED
        List<Booking> bookings = bookingRepository.findByRoomId(id);
        if (bookings != null && !bookings.isEmpty()) {
            for (Booking booking : bookings) {
                booking.setStatus("CANCELLED");
                bookingRepository.save(booking);
            }
        }

        // Remove all staff assignments
        Set<Staff> assignedStaff = room.getAssignedStaff();
        if (assignedStaff != null && !assignedStaff.isEmpty()) {
            assignedStaff.forEach(staff -> {
                staff.getAssignedRooms().remove(room);
            });
            room.getAssignedStaff().clear();
        }

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

    @Override
    public List<Room> getAvailableRooms() {
        return roomRepository.findByStatus("AVAILABLE");
    }

    @Override
    public List<Room> getRoomsByType(String type) {
        return roomRepository.findByType(type);
    }

    @Override
    public List<Room> getRoomsByPriceRange(Double minPrice, Double maxPrice) {
        return roomRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Room> getRoomsByCapacity(Integer capacity) {
        return roomRepository.findByCapacityGreaterThanEqual(capacity);
    }

    @Override
    public Room updateRoomStatus(Integer id, String status) {
        Room room = getRoomById(id);
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        // Validate status
        String upperStatus = status.toUpperCase();
        if (!upperStatus.equals("AVAILABLE") && !upperStatus.equals("OCCUPIED") &&
            !upperStatus.equals("MAINTENANCE") && !upperStatus.equals("OUT_OF_SERVICE")) {
            throw new IllegalArgumentException("Invalid status. Allowed values are: AVAILABLE, OCCUPIED, MAINTENANCE, OUT_OF_SERVICE");
        }

        room.setStatus(upperStatus);
        return roomRepository.save(room);
    }
}
