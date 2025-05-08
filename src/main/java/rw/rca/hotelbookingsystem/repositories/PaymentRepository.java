/**
 * Repository interface for managing Payment entities in the database.
 * Provides methods for basic CRUD operations and custom queries for payment management.
 */
package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.Payment;
import rw.rca.hotelbookingsystem.models.PaymentStatus;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    /**
     * Finds all payments for a specific booking
     * @param bookingId The ID of the booking to find payments for
     * @return List of payments associated with the specified booking
     */
    List<Payment> findByBooking_BookingID(Integer bookingId);

    /**
     * Finds all payments with a specific status
     * @param status The payment status to search for
     * @return List of payments with the specified status
     */
    List<Payment> findByStatus(PaymentStatus status);
}
