package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.Address;
import rw.rca.hotelbookingsystem.services.BookingService;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Override
    public Address.Booking createBooking(Address.Booking booking) {
        // TODO: Implement booking creation logic
        return booking;
    }

    @Override
    public Address.Booking getBookingById(Long id) {
        // TODO: Implement get booking by ID logic
        return null;
    }

    @Override
    public List<Address.Booking> getUserBookings(Long userId) {
        // TODO: Implement get user bookings logic
        return null;
    }

    @Override
    public Address.Booking cancelBooking(Long id) {
        // TODO: Implement cancel booking logic
        return null;
    }

    @Override
    public Address.Booking checkIn(Long id) {
        // TODO: Implement check-in logic
        return null;
    }

    @Override
    public Address.Booking checkOut(Long id) {
        // TODO: Implement check-out logic
        return null;
    }

    @Override
    public List<Address.Booking> getRoomBookings(Long roomId) {
        // TODO: Implement get room bookings logic
        return null;
    }
}
