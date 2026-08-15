package com.bridgelabz.service;

import com.bridgelabz.dto.request.TravelPackageRequestDTO;
import com.bridgelabz.dto.response.TravelPackageResponseDTO;
import com.bridgelabz.exception.DestinationNotFoundException;
import com.bridgelabz.exception.TravelPackageNotFoundException;
import com.bridgelabz.model.Destination;
import com.bridgelabz.model.TravelPackage;
import com.bridgelabz.repository.DestinationRepository;
import com.bridgelabz.repository.TravelPackageRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TravelPackageServiceTest {

    @Mock
    private TravelPackageRepository travelPackageRepository;

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private TravelPackageService travelPackageService;


    // CREATE PACKAGE
    @Test
    void createPackage_shouldReturnPackage() {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        5,
                        1L
                );

        Destination destination = new Destination();

        destination.setId(1L);
        destination.setName("Paris");
        destination.setCountry("France");


        TravelPackage savedPackage = new TravelPackage();

        savedPackage.setId(1L);
        savedPackage.setPackageName("Paris Holiday");
        savedPackage.setPrice(new BigDecimal("50000"));
        savedPackage.setDuration(5);
        savedPackage.setDestination(destination);


        when(destinationRepository.findById(1L))
                .thenReturn(Optional.of(destination));

        when(travelPackageRepository.save(any(TravelPackage.class)))
                .thenReturn(savedPackage);


        TravelPackageResponseDTO response =
                travelPackageService.createPackage(request);


        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Paris Holiday", response.getPackageName());
        assertEquals(new BigDecimal("50000"), response.getPrice());
        assertEquals(5, response.getDuration());
        assertEquals(1L, response.getDestinationId());


        verify(destinationRepository, times(1))
                .findById(1L);

        verify(travelPackageRepository, times(1))
                .save(any(TravelPackage.class));
    }


    // CREATE PACKAGE - DESTINATION NOT FOUND
    @Test
    void createPackage_whenDestinationNotFound_shouldThrowException() {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        5,
                        999L
                );

        when(destinationRepository.findById(999L))
                .thenReturn(Optional.empty());


        assertThrows(
                DestinationNotFoundException.class,
                () -> travelPackageService.createPackage(request)
        );


        verify(destinationRepository, times(1))
                .findById(999L);

        verify(travelPackageRepository, never())
                .save(any(TravelPackage.class));
    }


    // GET PACKAGE BY ID
    @Test
    void getPackageById_shouldReturnPackage() {

        Destination destination = new Destination();
        destination.setId(1L);

        TravelPackage travelPackage = new TravelPackage();

        travelPackage.setId(1L);
        travelPackage.setPackageName("Paris Holiday");
        travelPackage.setPrice(new BigDecimal("50000"));
        travelPackage.setDuration(5);
        travelPackage.setDestination(destination);


        when(travelPackageRepository.findById(1L))
                .thenReturn(Optional.of(travelPackage));


        TravelPackageResponseDTO response =
                travelPackageService.getPackageById(1L);


        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Paris Holiday", response.getPackageName());
        assertEquals(5, response.getDuration());
        assertEquals(1L, response.getDestinationId());


        verify(travelPackageRepository, times(1))
                .findById(1L);
    }


    // PACKAGE NOT FOUND
    @Test
    void getPackageById_whenNotFound_shouldThrowException() {

        when(travelPackageRepository.findById(999L))
                .thenReturn(Optional.empty());


        assertThrows(
                TravelPackageNotFoundException.class,
                () -> travelPackageService.getPackageById(999L)
        );


        verify(travelPackageRepository, times(1))
                .findById(999L);
    }


    // GET ALL PACKAGES
    @Test
    void getAllPackages_shouldReturnList() {

        Destination destination = new Destination();
        destination.setId(1L);

        TravelPackage travelPackage = new TravelPackage();

        travelPackage.setId(1L);
        travelPackage.setPackageName("Paris Holiday");
        travelPackage.setPrice(new BigDecimal("50000"));
        travelPackage.setDuration(5);
        travelPackage.setDestination(destination);


        when(travelPackageRepository.findAll())
                .thenReturn(List.of(travelPackage));


        List<TravelPackageResponseDTO> response =
                travelPackageService.getAllPackages();


        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(
                "Paris Holiday",
                response.get(0).getPackageName()
        );
        assertEquals(
                new BigDecimal("50000"),
                response.get(0).getPrice()
        );


        verify(travelPackageRepository, times(1))
                .findAll();
    }


    // UPDATE PACKAGE
    @Test
    void updatePackage_shouldReturnUpdatedPackage() {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "Paris Premium Holiday",
                        new BigDecimal("75000"),
                        7,
                        1L
                );


        Destination destination = new Destination();
        destination.setId(1L);


        TravelPackage travelPackage = new TravelPackage();

        travelPackage.setId(1L);
        travelPackage.setPackageName("Paris Holiday");
        travelPackage.setPrice(new BigDecimal("50000"));
        travelPackage.setDuration(5);
        travelPackage.setDestination(destination);


        when(travelPackageRepository.findById(1L))
                .thenReturn(Optional.of(travelPackage));

        when(destinationRepository.findById(1L))
                .thenReturn(Optional.of(destination));

        when(travelPackageRepository.save(any(TravelPackage.class)))
                .thenReturn(travelPackage);


        TravelPackageResponseDTO response =
                travelPackageService.updatePackage(1L, request);


        assertNotNull(response);
        assertEquals(
                "Paris Premium Holiday",
                response.getPackageName()
        );
        assertEquals(
                new BigDecimal("75000"),
                response.getPrice()
        );
        assertEquals(7, response.getDuration());
        assertEquals(1L, response.getDestinationId());


        verify(travelPackageRepository, times(1))
                .findById(1L);

        verify(destinationRepository, times(1))
                .findById(1L);

        verify(travelPackageRepository, times(1))
                .save(any(TravelPackage.class));
    }


    // UPDATE - PACKAGE NOT FOUND
    @Test
    void updatePackage_whenPackageNotFound_shouldThrowException() {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        5,
                        1L
                );


        when(travelPackageRepository.findById(999L))
                .thenReturn(Optional.empty());


        assertThrows(
                TravelPackageNotFoundException.class,
                () -> travelPackageService.updatePackage(
                        999L,
                        request
                )
        );


        verify(travelPackageRepository, times(1))
                .findById(999L);

        verify(destinationRepository, never())
                .findById(any(Long.class));
    }


    // UPDATE - DESTINATION NOT FOUND
    @Test
    void updatePackage_whenDestinationNotFound_shouldThrowException() {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        5,
                        999L
                );


        Destination destination = new Destination();
        destination.setId(1L);


        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setId(1L);
        travelPackage.setDestination(destination);


        when(travelPackageRepository.findById(1L))
                .thenReturn(Optional.of(travelPackage));

        when(destinationRepository.findById(999L))
                .thenReturn(Optional.empty());


        assertThrows(
                DestinationNotFoundException.class,
                () -> travelPackageService.updatePackage(
                        1L,
                        request
                )
        );


        verify(destinationRepository, times(1))
                .findById(999L);

        verify(travelPackageRepository, never())
                .save(any(TravelPackage.class));
    }


    // DELETE PACKAGE
    @Test
    void deletePackage_shouldDeletePackage() {

        Destination destination = new Destination();
        destination.setId(1L);

        TravelPackage travelPackage = new TravelPackage();

        travelPackage.setId(1L);
        travelPackage.setPackageName("Paris Holiday");
        travelPackage.setDestination(destination);


        when(travelPackageRepository.findById(1L))
                .thenReturn(Optional.of(travelPackage));


        travelPackageService.deletePackage(1L);


        verify(travelPackageRepository, times(1))
                .findById(1L);

        verify(travelPackageRepository, times(1))
                .delete(travelPackage);
    }


    // DELETE - PACKAGE NOT FOUND
    @Test
    void deletePackage_whenNotFound_shouldThrowException() {

        when(travelPackageRepository.findById(999L))
                .thenReturn(Optional.empty());


        assertThrows(
                TravelPackageNotFoundException.class,
                () -> travelPackageService.deletePackage(999L)
        );


        verify(travelPackageRepository, never())
                .delete(any(TravelPackage.class));
    }


    // GET PACKAGES BY DESTINATION
    @Test
    void getPackagesByDestination_shouldReturnPackages() {

        Destination destination = new Destination();
        destination.setId(1L);


        TravelPackage travelPackage = new TravelPackage();

        travelPackage.setId(1L);
        travelPackage.setPackageName("Paris Holiday");
        travelPackage.setPrice(new BigDecimal("50000"));
        travelPackage.setDuration(5);
        travelPackage.setDestination(destination);


        when(destinationRepository.existsById(1L))
                .thenReturn(true);

        when(travelPackageRepository
                .findByDestination_Id(1L))
                .thenReturn(List.of(travelPackage));


        List<TravelPackageResponseDTO> response =
                travelPackageService
                        .getPackagesByDestination(1L);


        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(
                "Paris Holiday",
                response.get(0).getPackageName()
        );
        assertEquals(
                1L,
                response.get(0).getDestinationId()
        );


        verify(destinationRepository, times(1))
                .existsById(1L);

        verify(travelPackageRepository, times(1))
                .findByDestination_Id(1L);
    }


    // DESTINATION NOT FOUND
    @Test
    void getPackagesByDestination_whenDestinationNotFound()
            throws Exception {

        when(destinationRepository.existsById(999L))
                .thenReturn(false);


        assertThrows(
                DestinationNotFoundException.class,
                () -> travelPackageService
                        .getPackagesByDestination(999L)
        );


        verify(destinationRepository, times(1))
                .existsById(999L);

        verify(travelPackageRepository, never())
                .findByDestination_Id(any(Long.class));
    }
}