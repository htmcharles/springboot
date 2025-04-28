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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingID;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference(value = "room-bookings")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-bookings")
    private User user;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Temporal(TemporalType.DATE)
    private Date checkOut;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    @Column(nullable = false)
    private String status;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "additional_requests")
    private String additionalRequests;

    @JsonProperty("userId")
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    @JsonProperty("userFirstName")
    public String getUserFirstName() {
        return user != null ? user.getFirstName() : null;
    }

    @JsonProperty("userLastName")
    public String getUserLastName() {
        return user != null ? user.getLastName() : null;
    }

    @JsonProperty("userEmail")
    public String getUserEmail() {
        return user != null ? user.getEmail() : null;
    }

    public Booking(Integer bookingID, Room room, User user, Date checkIn, Date checkOut, Double totalPrice) {
        this.bookingID = bookingID;
        this.room = room;
        this.user = user;
        this.checkInDate = checkIn.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        this.checkOut = checkOut;
        this.status = "PENDING";
        this.totalPrice = totalPrice;
    }

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
