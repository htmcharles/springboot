package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guestID;

    private String name;
    private String phone;
    private String email;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    public Guest(Integer guestID, String name, String phone, String email, Address address) {
        this.guestID = guestID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Guest() {

    }

    public Integer getGuestID() {
        return guestID;
    }

    public void setGuestID(Integer guestID) {
        this.guestID = guestID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    // Helper method to add booking
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setGuest(this);
    }

    // Helper method to remove booking
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setGuest(null);
    }
}
