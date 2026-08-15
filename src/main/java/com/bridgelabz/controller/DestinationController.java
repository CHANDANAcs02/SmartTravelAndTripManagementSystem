package com.bridgelabz.controller;

import com.bridgelabz.dto.request.DestinationRequestDTO;
import com.bridgelabz.dto.response.DestinationResponseDTO;
import com.bridgelabz.service.DestinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(
            DestinationService destinationService) {

        this.destinationService = destinationService;
    }

    // CREATE DESTINATION
    @PostMapping
    public ResponseEntity<DestinationResponseDTO> createDestination(
            @Valid @RequestBody DestinationRequestDTO request) {

        DestinationResponseDTO response =
                destinationService.createDestination(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET ALL DESTINATIONS
    @GetMapping
    public ResponseEntity<List<DestinationResponseDTO>>
    getAllDestinations() {

        List<DestinationResponseDTO> destinations =
                destinationService.getAllDestinations();

        return ResponseEntity.ok(destinations);
    }

    // GET DESTINATION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponseDTO>
    getDestinationById(@PathVariable Long id) {

        DestinationResponseDTO response =
                destinationService.getDestinationById(id);

        return ResponseEntity.ok(response);
    }
}