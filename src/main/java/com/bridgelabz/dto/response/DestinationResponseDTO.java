package com.bridgelabz.dto.response;

public class DestinationResponseDTO {

    private Long id;
    private String name;
    private String country;
    private String description;

    public DestinationResponseDTO() {
    }

    public DestinationResponseDTO(
            Long id,
            String name,
            String country,
            String description) {

        this.id = id;
        this.name = name;
        this.country = country;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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