package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.controllers.StaffController;

import java.util.List;

public interface RoomService {
    List<StaffController.Room> getAllRooms();
    StaffController.Room getRoomById(Long id);
    StaffController.Room createRoom(StaffController.Room room);
    StaffController.Room updateRoom(Long id, StaffController.Room room);
    void deleteRoom(Long id);
    List<StaffController.Room> searchRooms(String type, Double minPrice, Double maxPrice, Integer capacity);
    List<StaffController.Room> getAvailableRooms(String checkInDate, String checkOutDate);
}
