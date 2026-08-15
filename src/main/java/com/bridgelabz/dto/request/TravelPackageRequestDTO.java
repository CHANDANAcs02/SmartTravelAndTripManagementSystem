package com.bridgelabz.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TravelPackageRequestDTO {

    @NotBlank(message = "Package name is required")
    private String packageName;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    private Integer duration;

    @NotNull(message = "Destination ID is required")
    private Long destinationId;

    public TravelPackageRequestDTO() {
    }

    public TravelPackageRequestDTO(
            String packageName,
            BigDecimal price,
            Integer duration,
            Long destinationId) {

        this.packageName = packageName;
        this.price = price;
        this.duration = duration;
        this.destinationId = destinationId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }
}