/**
 * Review entity representing a user's review of a hotel room.
 * This class manages review information including ratings, comments,
 * and timestamps for creation and updates.
 */
package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    /**
     * Unique identifier for the review
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who wrote the review
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-reviews")
    private User user;

    /**
     * The room being reviewed
     */
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    /**
     * Rating given by the user (typically 1-5)
     */
    @Column(nullable = false)
    private Integer rating;

    /**
     * Written comment or feedback about the room
     */
    @Column(nullable = false)
    private String comment;

    /**
     * Date and time when the review was created
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Date and time when the review was last updated
     */
    @Column
    private LocalDateTime updatedAt;

    /**
     * Gets the ID of the user who wrote the review
     * @return User ID or null if no user is associated
     */
    @JsonProperty("userId")
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    /**
     * Gets the first name of the user who wrote the review
     * @return User's first name or null if no user is associated
     */
    @JsonProperty("userFirstName")
    public String getUserFirstName() {
        return user != null ? user.getFirstName() : null;
    }

    /**
     * Gets the last name of the user who wrote the review
     * @return User's last name or null if no user is associated
     */
    @JsonProperty("userLastName")
    public String getUserLastName() {
        return user != null ? user.getLastName() : null;
    }

    /**
     * Gets the email of the user who wrote the review
     * @return User's email or null if no user is associated
     */
    @JsonProperty("userEmail")
    public String getUserEmail() {
        return user != null ? user.getEmail() : null;
    }

    /**
     * Default constructor initializes creation timestamp
     */
    public Review() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Constructor for creating a new review with all required fields
     * @param user The user writing the review
     * @param room The room being reviewed
     * @param rating Rating given by the user
     * @param comment Written feedback about the room
     */
    public Review(User user, Room room, Integer rating, String comment) {
        this.user = user;
        this.room = room;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Updates the review with new rating and comment
     * @param rating New rating value
     * @param comment New comment text
     */
    public void updateReview(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
