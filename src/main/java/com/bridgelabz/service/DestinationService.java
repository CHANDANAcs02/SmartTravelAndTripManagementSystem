package com.bridgelabz.service;

import com.bridgelabz.dto.request.DestinationRequestDTO;
import com.bridgelabz.dto.response.DestinationResponseDTO;
import com.bridgelabz.exception.DestinationNotFoundException;
import com.bridgelabz.model.Destination;
import com.bridgelabz.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(
            DestinationRepository destinationRepository) {

        this.destinationRepository = destinationRepository;
    }

    // CREATE DESTINATION
    public DestinationResponseDTO createDestination(
            DestinationRequestDTO request) {

        Destination destination = new Destination();

        destination.setName(request.getName());
        destination.setCountry(request.getCountry());
        destination.setDescription(request.getDescription());

        Destination savedDestination =
                destinationRepository.save(destination);

        return convertToResponse(savedDestination);
    }

    // GET ALL DESTINATIONS
    public List<DestinationResponseDTO> getAllDestinations() {

        return destinationRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET DESTINATION BY ID
    public DestinationResponseDTO getDestinationById(Long id) {

        Destination destination =
                destinationRepository.findById(id)
                        .orElseThrow(() ->
                                new DestinationNotFoundException(
                                        "Destination not found with id: "
                                                + id
                                ));

        return convertToResponse(destination);
    }

    // ENTITY -> RESPONSE DTO
    private DestinationResponseDTO convertToResponse(
            Destination destination) {

        return new DestinationResponseDTO(
                destination.getId(),
                destination.getName(),
                destination.getCountry(),
                destination.getDescription()
        );
    }
}