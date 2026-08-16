package com.bridgelabz.service;

import com.bridgelabz.dto.request.ReviewRequestDTO;
import com.bridgelabz.dto.response.ReviewResponseDTO;
import com.bridgelabz.exception.UserNotFoundException;
import com.bridgelabz.exception.ReviewNotFoundException;
import com.bridgelabz.exception.TravelPackageNotFoundException;
import com.bridgelabz.model.Review;
import com.bridgelabz.model.User;
import com.bridgelabz.model.TravelPackage;
import com.bridgelabz.repository.ReviewRepository;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.repository.TravelPackageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TravelPackageRepository travelPackageRepository;
    @Autowired
    private NotificationService notificationService;

    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         TravelPackageRepository travelPackageRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.travelPackageRepository = travelPackageRepository;
    }

    // Creates a review after checking that the user and travel package exist.
    public ReviewResponseDTO createReview(ReviewRequestDTO request) {

        // Find the user who is submitting the review.
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + request.getUserId()
                        ));

        // Find the travel package being reviewed.
        TravelPackage travelPackage = travelPackageRepository
                .findById(request.getTravelPackageId())
                .orElseThrow(() ->
                        new TravelPackageNotFoundException(
                                "Travel package not found with id: "
                                        + request.getTravelPackageId()
                        ));

        // Convert the request DTO into a Review entity.
        Review review = new Review();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUser(user);
        review.setTravelPackage(travelPackage);

        // Save the review in the database.
        Review savedReview = reviewRepository.save(review);

        //creating notification for user save
        notificationService.createNotification(user,"Your review was added successfully.","REVIEW");
        return convertToResponse(savedReview);
    }

    // Gets all reviews belonging to a particular travel package.
    public List<ReviewResponseDTO> getReviewsByPackage(Long packageId) {

        // Make sure the package exists before searching for its reviews.
        travelPackageRepository.findById(packageId)
                .orElseThrow(() ->
                        new TravelPackageNotFoundException(
                                "Travel package not found with id: " + packageId
                        ));

        return reviewRepository.findByTravelPackageId(packageId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }
 // Updates an existing review with the values received from the client.
    public ReviewResponseDTO updateReview(Long reviewId, ReviewRequestDTO request) {

        // Find the review that needs to be updated.
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                new ReviewNotFoundException(
                                "Review not found with id: " + reviewId
                        ));

        // Check that the new user exists.
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + request.getUserId()
                        ));

        // Check that the new travel package exists.
        TravelPackage travelPackage = travelPackageRepository
                .findById(request.getTravelPackageId())
                .orElseThrow(() ->
                        new TravelPackageNotFoundException(
                                "Travel package not found with id: "
                                        + request.getTravelPackageId()
                        ));

        // Update the existing review instead of creating a new review.
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUser(user);
        review.setTravelPackage(travelPackage);

        // Save the updated review in the database.
        Review updatedReview = reviewRepository.save(review);

        return convertToResponse(updatedReview);
    }

    // Converts the Review entity into a response DTO.
    private ReviewResponseDTO convertToResponse(Review review) {

        return new ReviewResponseDTO(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getUser().getId(),
                review.getTravelPackage().getId()
        );
    }
}
