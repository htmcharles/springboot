package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.Booking;
import rw.rca.hotelbookingsystem.repositories.BookingRepository;
import rw.rca.hotelbookingsystem.services.BookingService;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    @Override
    public List<Booking> getUserBookings(Integer userId) {
        List<Booking> bookings = bookingRepository.findByGuestGuestID(userId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("No bookings found for user with ID: " + userId);
        }
        return bookings;
    }

    @Override
    public Booking cancelBooking(Integer id) {
        Booking booking = getBookingById(id);
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking is already cancelled");
        }
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    @Override
    public Booking checkIn(Integer id) {
        Booking booking = getBookingById(id);
        if ("CHECKED_IN".equals(booking.getStatus())) {
            throw new RuntimeException("Booking is already checked in");
        }
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Cannot check in a cancelled booking");
        }
        booking.setStatus("CHECKED_IN");
        return bookingRepository.save(booking);
    }

    @Override
    public Booking checkOut(Integer id) {
        Booking booking = getBookingById(id);
        if ("CHECKED_OUT".equals(booking.getStatus())) {
            throw new RuntimeException("Booking is already checked out");
        }
        if (!"CHECKED_IN".equals(booking.getStatus())) {
            throw new RuntimeException("Cannot check out a booking that is not checked in");
        }
        booking.setStatus("CHECKED_OUT");
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getRoomBookings(Integer roomId) {
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("No bookings found for room with ID: " + roomId);
        }
        return bookings;
    }
}
