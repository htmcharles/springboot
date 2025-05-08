/**
 * Repository interface for managing Review entities in the database.
 * Provides methods for basic CRUD operations and custom queries for review management.
 */
package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.rca.hotelbookingsystem.models.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * Finds all reviews for a specific room
     * @param roomId The ID of the room to find reviews for
     * @return List of reviews for the specified room
     */
    List<Review> findByRoomId(Long roomId);

    /**
     * Finds all reviews written by a specific user
     * @param userId The ID of the user to find reviews for
     * @return List of reviews written by the specified user
     */
    List<Review> findByUser_Id(Long userId);

    /**
     * Calculates the average rating for a specific room
     * @param roomId The ID of the room to calculate average rating for
     * @return The average rating for the room, or null if no reviews exist
     */
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.room.id = ?1")
    Double findAverageRatingByRoomId(Long roomId);
}
