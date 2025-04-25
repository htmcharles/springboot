package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Booking;
import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking getBookingById(Integer id);
    List<Booking> getUserBookings(Integer userId);
    Booking cancelBooking(Integer id);
    Booking checkIn(Integer id);
    Booking checkOut(Integer id);
    List<Booking> getRoomBookings(Integer roomId);
}
