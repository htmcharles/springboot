/**
 * Service interface for managing booking-related business logic.
 * Provides methods for creating, managing, and tracking room bookings.
 */
package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Booking;
import java.util.List;

public interface BookingService {
    /**
     * Creates a new booking in the system
     * @param booking The booking object containing booking details
     * @return The created booking with generated ID
     */
    Booking createBooking(Booking booking);

    /**
     * Retrieves a specific booking by its ID
     * @param id The ID of the booking to retrieve
     * @return The booking with the specified ID
     */
    Booking getBookingById(Integer id);

    /**
     * Retrieves all bookings made by a specific user
     * @param userId The ID of the user to get bookings for
     * @return List of bookings made by the user
     */
    List<Booking> getUserBookings(Long userId);

    /**
     * Cancels a booking and updates its status
     * @param id The ID of the booking to cancel
     * @return The updated booking with cancelled status
     */
    Booking cancelBooking(Integer id);

    /**
     * Processes check-in for a booking
     * @param id The ID of the booking to check in
     * @return The updated booking with checked-in status
     */
    Booking checkIn(Integer id);

    /**
     * Processes check-out for a booking
     * @param id The ID of the booking to check out
     * @return The updated booking with checked-out status
     */
    Booking checkOut(Integer id);

    /**
     * Retrieves all bookings for a specific room
     * @param roomId The ID of the room to get bookings for
     * @return List of bookings for the room
     */
    List<Booking> getRoomBookings(Integer roomId);

    /**
     * Retrieves all bookings in the system
     * @return List of all bookings
     */
    List<Booking> getAllBookings();
}
