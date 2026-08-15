package com.bridgelabz.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DestinationRequestDTO {

    @NotBlank(message = "Destination name is required")
    @Size(
            max = 100,
            message = "Destination name cannot exceed 100 characters"
    )
    private String name;

    @NotBlank(message = "Country is required")
    @Size(
            max = 100,
            message = "Country cannot exceed 100 characters"
    )
    private String country;

    @Size(
            max = 500,
            message = "Description cannot exceed 500 characters"
    )
    private String description;

    public DestinationRequestDTO() {
    }

    public DestinationRequestDTO(
            String name,
            String country,
            String description) {

        this.name = name;
        this.country = country;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}