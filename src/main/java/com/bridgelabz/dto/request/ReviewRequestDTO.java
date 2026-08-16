package com.bridgelabz.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestDTO {

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    @NotBlank(message = "Comment is required")
    private String comment;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Travel package ID is required")
    private Long travelPackageId;

    public ReviewRequestDTO() {
    }
    
    public ReviewRequestDTO(
			@NotNull(message = "Rating is required") @Min(value = 1, message = "Rating must be at least 1") @Max(value = 5, message = "Rating must not exceed 5") Integer rating,
			@NotBlank(message = "Comment is required") String comment,
			@NotNull(message = "User ID is required") Long userId,
			@NotNull(message = "Travel package ID is required") Long travelPackageId) {
		super();
		this.rating = rating;
		this.comment = comment;
		this.userId = userId;
		this.travelPackageId = travelPackageId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTravelPackageId() {
        return travelPackageId;
    }

    public void setTravelPackageId(Long travelPackageId) {
        this.travelPackageId = travelPackageId;
    }
}