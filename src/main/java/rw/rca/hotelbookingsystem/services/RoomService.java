package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Room;
import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    Room getRoomById(Integer id);
    Room createRoom(Room room);
    Room updateRoom(Integer id, Room room);
    void deleteRoom(Integer id);
    List<Room> searchRooms(String type, Double minPrice, Double maxPrice, Integer capacity);
    List<Room> getAvailableRooms(String checkInDate, String checkOutDate);
    List<Room> getAvailableRooms();
    List<Room> getRoomsByType(String type);
    List<Room> getRoomsByPriceRange(Double minPrice, Double maxPrice);
    List<Room> getRoomsByCapacity(Integer capacity);
    Room updateRoomStatus(Integer id, String status);
}
