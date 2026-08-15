package com.bridgelabz.service;

import com.bridgelabz.dto.request.DestinationRequestDTO;
import com.bridgelabz.dto.response.DestinationResponseDTO;
import com.bridgelabz.exception.DestinationNotFoundException;
import com.bridgelabz.model.Destination;
import com.bridgelabz.repository.DestinationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DestinationServiceTest {

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private DestinationService destinationService;


    // CREATE DESTINATION
    @Test
    void createDestination_shouldReturnDestination() {

        DestinationRequestDTO request =
                new DestinationRequestDTO(
                        "Paris",
                        "France",
                        "City of love"
                );

        Destination savedDestination = new Destination();

        savedDestination.setId(1L);
        savedDestination.setName("Paris");
        savedDestination.setCountry("France");
        savedDestination.setDescription("City of love");

        when(destinationRepository.save(any(Destination.class)))
                .thenReturn(savedDestination);

        DestinationResponseDTO response =
                destinationService.createDestination(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Paris", response.getName());
        assertEquals("France", response.getCountry());
        assertEquals("City of love", response.getDescription());

        verify(destinationRepository, times(1))
                .save(any(Destination.class));
    }


    // GET ALL DESTINATIONS
    @Test
    void getAllDestinations_shouldReturnList() {

        Destination destination = new Destination();

        destination.setId(1L);
        destination.setName("Paris");
        destination.setCountry("France");
        destination.setDescription("City of love");

        when(destinationRepository.findAll())
                .thenReturn(List.of(destination));

        List<DestinationResponseDTO> response =
                destinationService.getAllDestinations();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Paris", response.get(0).getName());
        assertEquals("France", response.get(0).getCountry());

        verify(destinationRepository, times(1))
                .findAll();
    }


    // GET DESTINATION BY ID
    @Test
    void getDestinationById_shouldReturnDestination() {

        Destination destination = new Destination();

        destination.setId(1L);
        destination.setName("Paris");
        destination.setCountry("France");
        destination.setDescription("City of love");

        when(destinationRepository.findById(1L))
                .thenReturn(Optional.of(destination));

        DestinationResponseDTO response =
                destinationService.getDestinationById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Paris", response.getName());
        assertEquals("France", response.getCountry());

        verify(destinationRepository, times(1))
                .findById(1L);
    }


    // DESTINATION NOT FOUND
    @Test
    void getDestinationById_whenNotFound_shouldThrowException() {

        when(destinationRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                DestinationNotFoundException.class,
                () -> destinationService.getDestinationById(999L)
        );

        verify(destinationRepository, times(1))
                .findById(999L);
    }
}