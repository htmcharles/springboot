/**
 * Room entity representing a hotel room in the booking system.
 * This class stores room information including its type, price, status,
 * and relationships with bookings and staff assignments.
 */
package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class Room {

    /**
     * Unique identifier for the room
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Unique room number identifier
     */
    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    /**
     * Type of the room (e.g., Standard, Deluxe, Suite)
     */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    /**
     * Price per night for the room
     */
    @Column(name = "price", nullable = false)
    private Double price;

    /**
     * Current status of the room (e.g., AVAILABLE, OCCUPIED, MAINTENANCE)
     */
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    /**
     * Maximum number of guests the room can accommodate
     */
    @Column(name = "capacity")
    private Integer capacity;

    /**
     * List of bookings associated with this room
     */
    @JsonManagedReference(value = "room-bookings")
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    /**
     * Set of staff members assigned to maintain this room
     */
    @ManyToMany(mappedBy = "assignedRooms")
    private Set<Staff> assignedStaff = new HashSet<>();

    // Default constructor
    public Room() {}

    // Constructor with all fields including capacity
    public Room(Integer id, String roomNumber, String type, Double price, String status, Integer capacity) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.status = status;
        this.capacity = capacity;
    }

    // Alternative constructor including capacity
    public Room(String roomNumber, String type, Double price, String status, Integer capacity) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.status = status;
        this.capacity = capacity;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Adds a booking to the room's list of bookings
     * @param booking The booking to add
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setRoom(this);
    }

    /**
     * Removes a booking from the room's list of bookings
     * @param booking The booking to remove
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setRoom(null);
    }

    public Set<Staff> getAssignedStaff() {
        return assignedStaff;
    }

    public void setAssignedStaff(Set<Staff> assignedStaff) {
        this.assignedStaff = assignedStaff;
    }
}
