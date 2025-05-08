/**
 * Service interface for managing review-related business logic.
 * Provides methods for creating, managing, and analyzing room reviews.
 */
package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Review;
import java.util.List;
import java.util.Map;

public interface ReviewService {
    /**
     * Creates a new review in the system
     * @param review The review object containing review details
     * @return The created review with generated ID
     */
    Review createReview(Review review);

    /**
     * Retrieves a specific review by its ID
     * @param id The ID of the review to retrieve
     * @return The review with the specified ID
     */
    Review getReviewById(Long id);

    /**
     * Retrieves all reviews for a specific room with additional details
     * @param roomId The ID of the room to get reviews for
     * @return List of maps containing review details and user information
     */
    List<Map<String, Object>> getRoomReviews(Long roomId);

    /**
     * Retrieves all reviews written by a specific user
     * @param userId The ID of the user to get reviews for
     * @return List of reviews written by the user
     */
    List<Review> getUserReviews(Long userId);

    /**
     * Updates an existing review
     * @param id The ID of the review to update
     * @param review The review object containing updated information
     * @return The updated review
     */
    Review updateReview(Long id, Review review);

    /**
     * Deletes a review from the system
     * @param id The ID of the review to delete
     */
    void deleteReview(Long id);

    /**
     * Calculates the average rating for a specific room
     * @param roomId The ID of the room to calculate average rating for
     * @return The average rating for the room
     */
    Double getRoomAverageRating(Long roomId);

    /**
     * Retrieves all reviews in the system
     * @return List of all reviews
     */
    List<Review> getAllReviews();
}
