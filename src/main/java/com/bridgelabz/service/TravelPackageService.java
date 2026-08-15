package com.bridgelabz.service;

import com.bridgelabz.dto.request.TravelPackageRequestDTO;
import com.bridgelabz.dto.response.TravelPackageResponseDTO;
import com.bridgelabz.exception.DestinationNotFoundException;
import com.bridgelabz.exception.TravelPackageNotFoundException;
import com.bridgelabz.model.Destination;
import com.bridgelabz.model.TravelPackage;
import com.bridgelabz.repository.DestinationRepository;
import com.bridgelabz.repository.TravelPackageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;
    private final DestinationRepository destinationRepository;

    public TravelPackageService(
            TravelPackageRepository travelPackageRepository,
            DestinationRepository destinationRepository) {

        this.travelPackageRepository = travelPackageRepository;
        this.destinationRepository = destinationRepository;
    }

    // CREATE TRAVEL PACKAGE
    public TravelPackageResponseDTO createPackage(
            TravelPackageRequestDTO request) {

        Destination destination =
                destinationRepository.findById(
                        request.getDestinationId()
                ).orElseThrow(() ->
                        new DestinationNotFoundException(
                                "Destination not found with id: "
                                        + request.getDestinationId()
                        )
                );

        TravelPackage travelPackage = new TravelPackage();

        travelPackage.setPackageName(
                request.getPackageName()
        );

        travelPackage.setPrice(
                request.getPrice()
        );

        travelPackage.setDuration(
                request.getDuration()
        );

        travelPackage.setDestination(destination);

        TravelPackage savedPackage =
                travelPackageRepository.save(travelPackage);

        return convertToResponse(savedPackage);
    }

    // GET TRAVEL PACKAGE BY ID
    @Transactional(readOnly = true)
    public TravelPackageResponseDTO getPackageById(Long id) {

        TravelPackage travelPackage =
                travelPackageRepository.findById(id)
                        .orElseThrow(() ->
                                new TravelPackageNotFoundException(
                                        "Travel package not found with id: "
                                                + id
                                )
                        );

        return convertToResponse(travelPackage);
    }

    // GET ALL TRAVEL PACKAGES
    @Transactional(readOnly = true)
    public List<TravelPackageResponseDTO> getAllPackages() {

        return travelPackageRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // UPDATE TRAVEL PACKAGE
    public TravelPackageResponseDTO updatePackage(
            Long id,
            TravelPackageRequestDTO request) {

        TravelPackage travelPackage =
                travelPackageRepository.findById(id)
                        .orElseThrow(() ->
                                new TravelPackageNotFoundException(
                                        "Travel package not found with id: "
                                                + id
                                )
                        );

        Destination destination =
                destinationRepository.findById(
                        request.getDestinationId()
                ).orElseThrow(() ->
                        new DestinationNotFoundException(
                                "Destination not found with id: "
                                        + request.getDestinationId()
                        )
                );

        travelPackage.setPackageName(
                request.getPackageName()
        );

        travelPackage.setPrice(
                request.getPrice()
        );

        travelPackage.setDuration(
                request.getDuration()
        );

        travelPackage.setDestination(destination);

        TravelPackage updatedPackage =
                travelPackageRepository.save(travelPackage);

        return convertToResponse(updatedPackage);
    }

    // DELETE TRAVEL PACKAGE
    public void deletePackage(Long id) {

        TravelPackage travelPackage =
                travelPackageRepository.findById(id)
                        .orElseThrow(() ->
                                new TravelPackageNotFoundException(
                                        "Travel package not found with id: "
                                                + id
                                )
                        );

        travelPackageRepository.delete(travelPackage);
    }

    // GET ALL PACKAGES FOR A DESTINATION
    @Transactional(readOnly = true)
    public List<TravelPackageResponseDTO> getPackagesByDestination(
            Long destinationId) {

        // Verify that destination exists
        if (!destinationRepository.existsById(destinationId)) {

            throw new DestinationNotFoundException(
                    "Destination not found with id: "
                            + destinationId
            );
        }

        return travelPackageRepository
                .findByDestination_Id(destinationId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // ENTITY -> RESPONSE DTO
    private TravelPackageResponseDTO convertToResponse(
            TravelPackage travelPackage) {

        return new TravelPackageResponseDTO(
                travelPackage.getId(),
                travelPackage.getPackageName(),
                travelPackage.getPrice(),
                travelPackage.getDuration(),
                travelPackage.getDestination().getId()
        );
    }
}