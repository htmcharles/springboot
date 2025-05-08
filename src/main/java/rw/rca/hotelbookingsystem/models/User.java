/**
 * User entity representing a user in the hotel booking system.
 * This class stores user information including personal details, authentication data,
 * and relationships with bookings and reviews.
 */
package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = User.class
)
public class User {
    /**
     * Unique identifier for the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's first name
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * User's last name
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * User's email address (unique)
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * User's password (write-only for security)
     */
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * User's contact phone number
     */
    @Column(nullable = false)
    private String phoneNumber;

    /**
     * User's address information
     */
    @Embedded
    private Address address;

    /**
     * User's role in the system (e.g., ADMIN, CUSTOMER)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    /**
     * List of bookings made by the user
     */
    @JsonManagedReference(value = "user-bookings")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();

    /**
     * List of reviews written by the user
     */
    @JsonManagedReference(value = "user-reviews")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    // Default constructor
    public User() {}

    // Constructor with all fields
    public User(String firstName, String lastName, String email, String password,
                String phoneNumber, Address address, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a booking to the user's list of bookings
     * @param booking The booking to add
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setUser(this);
    }

    /**
     * Removes a booking from the user's list of bookings
     * @param booking The booking to remove
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setUser(null);
    }

    /**
     * Adds a review to the user's list of reviews
     * @param review The review to add
     */
    public void addReview(Review review) {
        reviews.add(review);
        review.setUser(this);
    }

    /**
     * Removes a review from the user's list of reviews
     * @param review The review to remove
     */
    public void removeReview(Review review) {
        reviews.remove(review);
        review.setUser(null);
    }
}
