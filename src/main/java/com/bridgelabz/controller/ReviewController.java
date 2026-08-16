package com.bridgelabz.controller;

import com.bridgelabz.dto.request.ReviewRequestDTO;
import com.bridgelabz.dto.response.ReviewResponseDTO;
import com.bridgelabz.service.ReviewService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Endpoint 16: Add a new review.
    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDTO> createReview(
            @Valid @RequestBody ReviewRequestDTO request) {

        ReviewResponseDTO response = reviewService.createReview(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Endpoint 17: Get all reviews for a travel package.
    @GetMapping("/packages/{id}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByPackage(
            @PathVariable("id") Long packageId) {

        List<ReviewResponseDTO> reviews =
                reviewService.getReviewsByPackage(packageId);

        return ResponseEntity.ok(reviews);
    }

    // Endpoint 18: Update an existing review.
    @PutMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(
            @PathVariable("id") Long reviewId,
            @Valid @RequestBody ReviewRequestDTO request) {

        ReviewResponseDTO response =
                reviewService.updateReview(reviewId, request);

        return ResponseEntity.ok(response);
    }
}