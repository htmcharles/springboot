/**
 * REST Controller for handling review-related HTTP requests.
 * Provides endpoints for creating, managing, and retrieving room reviews.
 */
package rw.rca.hotelbookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hotelbookingsystem.models.Review;
import rw.rca.hotelbookingsystem.services.ReviewService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Creates a new review in the system
     * @param review The review object containing review details
     * @return ResponseEntity containing the created review
     */
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.createReview(review));
    }

    /**
     * Retrieves a specific review by its ID
     * @param id The ID of the review to retrieve
     * @return ResponseEntity containing the review
     */
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    /**
     * Retrieves all reviews for a specific room with additional details
     * @param roomId The ID of the room to get reviews for
     * @return ResponseEntity containing list of reviews with user information
     */
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Map<String, Object>>> getRoomReviews(@PathVariable Long roomId) {
        return ResponseEntity.ok(reviewService.getRoomReviews(roomId));
    }

    /**
     * Retrieves all reviews written by a specific user
     * @param userId The ID of the user to get reviews for
     * @return ResponseEntity containing list of user's reviews
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getUserReviews(userId));
    }

    /**
     * Updates an existing review
     * @param id The ID of the review to update
     * @param review The review object containing updated information
     * @return ResponseEntity containing the updated review
     */
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }

    /**
     * Deletes a review from the system
     * @param id The ID of the review to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Calculates the average rating for a specific room
     * @param roomId The ID of the room to calculate average rating for
     * @return ResponseEntity containing the average rating
     */
    @GetMapping("/room/{roomId}/average-rating")
    public ResponseEntity<Double> getRoomAverageRating(@PathVariable Long roomId) {
        return ResponseEntity.ok(reviewService.getRoomAverageRating(roomId));
    }

    /**
     * Retrieves all reviews in the system
     * @return ResponseEntity containing list of all reviews
     */
    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }
}
