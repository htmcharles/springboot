package rw.rca.hotelbookingsystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rca.hotelbookingsystem.models.Review;
import rw.rca.hotelbookingsystem.models.Room;
import rw.rca.hotelbookingsystem.repositories.ReviewRepository;
import rw.rca.hotelbookingsystem.repositories.RoomRepository;
import rw.rca.hotelbookingsystem.services.ReviewService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Review createReview(Review review) {
        // Fetch the room from the database
        Room room = roomRepository.findById(review.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Set the room in the review
        review.setRoom(room);

        // Set the creation timestamp
        review.setCreatedAt(LocalDateTime.now());

        // Save the review
        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public List<Map<String, Object>> getRoomReviews(Long roomId) {
        return reviewRepository.findByRoomId(roomId).stream().map(review -> {
            Map<String, Object> simplifiedReview = new HashMap<>();
            simplifiedReview.put("id", review.getId());
            simplifiedReview.put("rating", review.getRating());
            simplifiedReview.put("comment", review.getComment());
            simplifiedReview.put("createdAt", review.getCreatedAt());
            return simplifiedReview;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Review> getUserReviews(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        // Streamline the response to include only necessary details
        return reviews.stream().map(review -> {
            Review simplifiedReview = new Review();
            simplifiedReview.setId(review.getId());
            simplifiedReview.setRating(review.getRating());
            simplifiedReview.setComment(review.getComment());
            simplifiedReview.setCreatedAt(review.getCreatedAt());
            simplifiedReview.setUpdatedAt(review.getUpdatedAt());
            // Set only essential room details
            Room room = new Room();
            room.setId(review.getRoom().getId());
            room.setRoomNumber(review.getRoom().getRoomNumber());
            room.setType(review.getRoom().getType());
            room.setPrice(review.getRoom().getPrice());
            simplifiedReview.setRoom(room);
            return simplifiedReview;
        }).collect(Collectors.toList());
    }

    @Override
    public Review updateReview(Long id, Review review) {
        Review existingReview = getReviewById(id);
        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());
        existingReview.setUpdatedAt(LocalDateTime.now());
        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Double getRoomAverageRating(Long roomId) {
        Double average = reviewRepository.findAverageRatingByRoomId(roomId);
        return average != null ? average : 0.0;
    }
}
