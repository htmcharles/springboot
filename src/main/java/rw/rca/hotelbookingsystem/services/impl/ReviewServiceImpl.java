package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.Review;
import rw.rca.hotelbookingsystem.services.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Override
    public Review createReview(Review review) {
        // TODO: Implement review creation logic
        return review;
    }

    @Override
    public Review getReviewById(Long id) {
        // TODO: Implement get review by ID logic
        return null;
    }

    @Override
    public List<Review> getRoomReviews(Long roomId) {
        // TODO: Implement get room reviews logic
        return null;
    }

    @Override
    public List<Review> getUserReviews(Long userId) {
        // TODO: Implement get user reviews logic
        return null;
    }

    @Override
    public Review updateReview(Long id, Review review) {
        // TODO: Implement update review logic
        return review;
    }

    @Override
    public void deleteReview(Long id) {
        // TODO: Implement delete review logic
    }

    @Override
    public Double getRoomAverageRating(Long roomId) {
        // TODO: Implement get room average rating logic
        return 0.0;
    }
}
