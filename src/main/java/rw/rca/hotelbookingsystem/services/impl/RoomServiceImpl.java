package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.controllers.StaffController;
import rw.rca.hotelbookingsystem.services.RoomService;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Override
    public List<StaffController.Room> getAllRooms() {
        // TODO: Implement get all rooms logic
        return null;
    }

    @Override
    public StaffController.Room getRoomById(Long id) {
        // TODO: Implement get room by ID logic
        return null;
    }

    @Override
    public StaffController.Room createRoom(StaffController.Room room) {
        // TODO: Implement create room logic
        return room;
    }

    @Override
    public StaffController.Room updateRoom(Long id, StaffController.Room room) {
        // TODO: Implement update room logic
        return room;
    }

    @Override
    public void deleteRoom(Long id) {
        // TODO: Implement delete room logic
    }

    @Override
    public List<StaffController.Room> searchRooms(String type, Double minPrice, Double maxPrice, Integer capacity) {
        // TODO: Implement search rooms logic
        return null;
    }

    @Override
    public List<StaffController.Room> getAvailableRooms(String checkInDate, String checkOutDate) {
        // TODO: Implement get available rooms logic
        return null;
    }
}
