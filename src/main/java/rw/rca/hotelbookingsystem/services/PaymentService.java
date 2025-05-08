/**
 * Service interface for managing payment-related business logic.
 * Provides methods for processing payments, refunds, and tracking payment status.
 */
package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Payment;
import java.util.List;

public interface PaymentService {
    /**
     * Processes a new payment for a booking
     * @param payment The payment object containing payment details
     * @return The processed payment with updated status
     */
    Payment processPayment(Payment payment);

    /**
     * Retrieves a specific payment by its ID
     * @param id The ID of the payment to retrieve
     * @return The payment with the specified ID
     */
    Payment getPaymentById(Integer id);

    /**
     * Retrieves all payments for a specific booking
     * @param bookingId The ID of the booking to get payments for
     * @return List of payments for the booking
     */
    List<Payment> getPaymentsByBooking(Integer bookingId);

    /**
     * Processes a refund for a payment
     * @param id The ID of the payment to refund
     * @return The updated payment with refund status
     */
    Payment processRefund(Integer id);

    /**
     * Retrieves all payments with a specific status
     * @param status The payment status to search for
     * @return List of payments with the specified status
     */
    List<Payment> getPaymentsByStatus(String status);

    /**
     * Retrieves all payments made by a specific user
     * @param userId The ID of the user to get payments for
     * @return List of payments made by the user
     */
    List<Payment> getPaymentsByUser(Long userId);

    /**
     * Retrieves all payments in the system
     * @return List of all payments
     */
    List<Payment> getAllPayments();

    /**
     * Updates an existing payment's information
     * @param payment The payment object containing updated information
     * @return The updated payment
     */
    Payment updatePayment(Payment payment);
}
