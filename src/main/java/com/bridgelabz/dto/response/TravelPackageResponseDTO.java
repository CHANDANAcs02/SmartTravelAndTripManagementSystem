package com.bridgelabz.dto.response;

import java.math.BigDecimal;

public class TravelPackageResponseDTO {

    private Long id;
    private String packageName;
    private BigDecimal price;
    private Integer duration;
    private Long destinationId;

    public TravelPackageResponseDTO() {
    }

    public TravelPackageResponseDTO(
            Long id,
            String packageName,
            BigDecimal price,
            Integer duration,
            Long destinationId) {

        this.id = id;
        this.packageName = packageName;
        this.price = price;
        this.duration = duration;
        this.destinationId = destinationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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