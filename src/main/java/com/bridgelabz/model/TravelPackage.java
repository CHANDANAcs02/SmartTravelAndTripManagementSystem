package com.bridgelabz.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "travel_packages")
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String packageName;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer duration;

    /*
     * MANY TravelPackages can belong to ONE Destination.
     *
     * This side owns the relationship.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "destination_id",
            nullable = false
    )
    private Destination destination;

    public TravelPackage() {
    }

    public TravelPackage(
            Long id,
            String packageName,
            BigDecimal price,
            Integer duration,
            Destination destination) {

        this.id = id;
        this.packageName = packageName;
        this.price = price;
        this.duration = duration;
        this.destination = destination;
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

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}