package com.bridgelabz.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    private String description;

    /*
     * ONE Destination can have MANY TravelPackages.
     *
     * The actual foreign key is maintained by TravelPackage.destination.
     */
    @OneToMany(
            mappedBy = "destination",
            cascade = CascadeType.ALL
    )
    private List<TravelPackage> packages = new ArrayList<>();

    public Destination() {
    }

    public Destination(
            Long id,
            String name,
            String country,
            String description,
            List<TravelPackage> packages) {

        this.id = id;
        this.name = name;
        this.country = country;
        this.description = description;
        this.packages = packages;
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

    public List<TravelPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<TravelPackage> packages) {
        this.packages = packages;
    }
}