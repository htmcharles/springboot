/**
 * Payment entity representing a payment transaction in the hotel booking system.
 * This class manages payment information including amount, status, dates,
 * and its relationship with bookings.
 */
package rw.rca.hotelbookingsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "paymentID"
)
public class Payment {
    /**
     * Unique identifier for the payment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID;

    /**
     * The booking associated with this payment
     */
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonIgnore
    private Booking booking;

    /**
     * Amount of the payment
     */
    private Double amount;

    /**
     * Current status of the payment (e.g., PENDING, COMPLETED, REFUNDED)
     */
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    /**
     * Date and time when the payment was made
     */
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    /**
     * Date and time when the payment was refunded (if applicable)
     */
    @Column(name = "refund_date")
    private LocalDateTime refundDate;

    /**
     * Method used for payment (e.g., CREDIT_CARD, CASH, BANK_TRANSFER)
     */
    @Column(name = "payment_method")
    private String paymentMethod;

    /**
     * Constructor for creating a new payment with all required fields
     * @param paymentID Unique identifier for the payment
     * @param booking The booking associated with this payment
     * @param amount Amount of the payment
     * @param status Current status of the payment
     * @param paymentMethod Method used for payment
     */
    public Payment(Integer paymentID, Booking booking, Double amount, PaymentStatus status, String paymentMethod) {
        this.paymentID = paymentID;
        this.booking = booking;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    /**
     * Default constructor
     */
    public Payment() {
    }

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDateTime getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDateTime refundDate) {
        this.refundDate = refundDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
