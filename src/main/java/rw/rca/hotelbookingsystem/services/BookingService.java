package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Address;

import java.util.List;

public interface BookingService {
    Address.Booking createBooking(Address.Booking booking);
    Address.Booking getBookingById(Long id);
    List<Address.Booking> getUserBookings(Long userId);
    Address.Booking cancelBooking(Long id);
    Address.Booking checkIn(Long id);
    Address.Booking checkOut(Long id);
    List<Address.Booking> getRoomBookings(Long roomId);
}
