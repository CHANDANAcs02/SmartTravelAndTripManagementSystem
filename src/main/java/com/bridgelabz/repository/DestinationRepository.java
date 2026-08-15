package com.bridgelabz.repository;

import com.bridgelabz.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository
        extends JpaRepository<Destination, Long> {
}