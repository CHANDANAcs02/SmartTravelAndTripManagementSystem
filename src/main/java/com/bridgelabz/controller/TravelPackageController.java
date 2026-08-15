package com.bridgelabz.controller;

import com.bridgelabz.dto.request.TravelPackageRequestDTO;
import com.bridgelabz.dto.response.TravelPackageResponseDTO;
import com.bridgelabz.service.TravelPackageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class TravelPackageController {

    private final TravelPackageService travelPackageService;

    public TravelPackageController(
            TravelPackageService travelPackageService) {

        this.travelPackageService = travelPackageService;
    }

    // CREATE TRAVEL PACKAGE
    @PostMapping
    public ResponseEntity<TravelPackageResponseDTO> createPackage(
            @Valid @RequestBody TravelPackageRequestDTO request) {

        TravelPackageResponseDTO response =
                travelPackageService.createPackage(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET ALL TRAVEL PACKAGES
    @GetMapping
    public ResponseEntity<List<TravelPackageResponseDTO>>
    getAllPackages() {

        List<TravelPackageResponseDTO> packages =
                travelPackageService.getAllPackages();

        return ResponseEntity.ok(packages);
    }

    // GET TRAVEL PACKAGE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TravelPackageResponseDTO>
    getPackageById(@PathVariable Long id) {

        TravelPackageResponseDTO response =
                travelPackageService.getPackageById(id);

        return ResponseEntity.ok(response);
    }

    // UPDATE TRAVEL PACKAGE
    @PutMapping("/{id}")
    public ResponseEntity<TravelPackageResponseDTO> updatePackage(
            @PathVariable Long id,
            @Valid @RequestBody TravelPackageRequestDTO request) {

        TravelPackageResponseDTO response =
                travelPackageService.updatePackage(id, request);

        return ResponseEntity.ok(response);
    }

    // DELETE TRAVEL PACKAGE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(
            @PathVariable Long id) {

        travelPackageService.deletePackage(id);

        return ResponseEntity.noContent().build();
    }

    // GET ALL PACKAGES FOR A DESTINATION
    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<TravelPackageResponseDTO>>
    getPackagesByDestination(
            @PathVariable Long destinationId) {

        List<TravelPackageResponseDTO> packages =
                travelPackageService
                        .getPackagesByDestination(destinationId);

        return ResponseEntity.ok(packages);
    }
}