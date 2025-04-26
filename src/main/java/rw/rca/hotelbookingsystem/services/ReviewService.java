package rw.rca.hotelbookingsystem.services;

import rw.rca.hotelbookingsystem.models.Review;
import java.util.List;
import java.util.Map;

public interface ReviewService {
    Review createReview(Review review);
    Review getReviewById(Long id);
    List<Map<String, Object>> getRoomReviews(Long roomId);
    List<Review> getUserReviews(Long userId);
    Review updateReview(Long id, Review review);
    void deleteReview(Long id);
    Double getRoomAverageRating(Long roomId);
}
