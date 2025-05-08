/**
 * Booking entity representing a hotel room reservation in the system.
 * This class manages the relationship between users and rooms, including
 * check-in/check-out dates, payment information, and booking status.
 */
package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "bookingID"
)
public class Booking {
    /**
     * Unique identifier for the booking
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingID;

    /**
     * The room being booked
     */
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference(value = "room-bookings")
    private Room room;

    /**
     * The user making the booking
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-bookings")
    private User user;

    /**
     * Date when the guest will check in
     */
    @Column(nullable = false)
    private LocalDate checkInDate;

    /**
     * Date when the guest will check out
     */
    @Temporal(TemporalType.DATE)
    private Date checkOut;

    /**
     * Payment information associated with this booking
     */
    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    /**
     * Current status of the booking (e.g., PENDING, CONFIRMED, CANCELLED)
     */
    @Column(nullable = false)
    private String status;

    /**
     * Total price for the entire booking period
     */
    @Column(name = "total_price")
    private Double totalPrice;

    /**
     * Any special requests or notes for the booking
     */
    @Column(name = "additional_requests")
    private String additionalRequests;

    /**
     * Gets the ID of the user who made the booking
     * @return User ID or null if no user is associated
     */
    @JsonProperty("userId")
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    /**
     * Gets the first name of the user who made the booking
     * @return User's first name or null if no user is associated
     */
    @JsonProperty("userFirstName")
    public String getUserFirstName() {
        return user != null ? user.getFirstName() : null;
    }

    /**
     * Gets the last name of the user who made the booking
     * @return User's last name or null if no user is associated
     */
    @JsonProperty("userLastName")
    public String getUserLastName() {
        return user != null ? user.getLastName() : null;
    }

    /**
     * Gets the email of the user who made the booking
     * @return User's email or null if no user is associated
     */
    @JsonProperty("userEmail")
    public String getUserEmail() {
        return user != null ? user.getEmail() : null;
    }

    /**
     * Constructor for creating a new booking with all required fields
     * @param bookingID Unique identifier for the booking
     * @param room The room being booked
     * @param user The user making the booking
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     * @param totalPrice Total price for the booking
     */
    public Booking(Integer bookingID, Room room, User user, Date checkIn, Date checkOut, Double totalPrice) {
        this.bookingID = bookingID;
        this.room = room;
        this.user = user;
        this.checkInDate = checkIn.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        this.checkOut = checkOut;
        this.status = "PENDING";
        this.totalPrice = totalPrice;
    }

    /**
     * Default constructor initializes booking with PENDING status
     */
    public Booking() {
        this.status = "PENDING";
    }

    public Integer getBookingID() {
        return bookingID;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAdditionalRequests() {
        return additionalRequests;
    }

    public void setAdditionalRequests(String additionalRequests) {
        this.additionalRequests = additionalRequests;
    }
}
