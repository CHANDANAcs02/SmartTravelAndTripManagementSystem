package com.bridgelabz.controller;

import com.bridgelabz.dto.request.TravelPackageRequestDTO;
import com.bridgelabz.dto.response.TravelPackageResponseDTO;
import com.bridgelabz.service.TravelPackageService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TravelPackageController.class)
class TravelPackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TravelPackageService travelPackageService;


    // UC-01: Create Travel Package
    @Test
    void createPackage_shouldReturn201() throws Exception {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        5,
                        1L
                );

        TravelPackageResponseDTO response =
                new TravelPackageResponseDTO(
                        1L,
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        5,
                        1L
                );

        when(travelPackageService.createPackage(
                any(TravelPackageRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/packages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.packageName")
                        .value("Paris Holiday"))
                .andExpect(jsonPath("$.price")
                        .value(50000))
                .andExpect(jsonPath("$.duration")
                        .value(5))
                .andExpect(jsonPath("$.destinationId")
                        .value(1));
    }


    // UC-02: Get All Travel Packages
    @Test
    void getAllPackages_shouldReturn200() throws Exception {

        TravelPackageResponseDTO packageResponse =
                new TravelPackageResponseDTO(
                        1L,
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        5,
                        1L
                );

        when(travelPackageService.getAllPackages())
                .thenReturn(List.of(packageResponse));

        mockMvc.perform(
                        get("/api/packages")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].packageName")
                        .value("Paris Holiday"))
                .andExpect(jsonPath("$[0].price")
                        .value(50000))
                .andExpect(jsonPath("$[0].duration")
                        .value(5))
                .andExpect(jsonPath("$[0].destinationId")
                        .value(1));
    }


    // UC-03: Get Travel Package By ID
    @Test
    void getPackageById_shouldReturn200() throws Exception {

        TravelPackageResponseDTO response =
                new TravelPackageResponseDTO(
                        1L,
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        5,
                        1L
                );

        when(travelPackageService.getPackageById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/packages/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.packageName")
                        .value("Paris Holiday"))
                .andExpect(jsonPath("$.price")
                        .value(50000))
                .andExpect(jsonPath("$.duration")
                        .value(5))
                .andExpect(jsonPath("$.destinationId")
                        .value(1));
    }


    // UC-04: Update Travel Package
    @Test
    void updatePackage_shouldReturn200() throws Exception {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "Paris Premium Holiday",
                        new BigDecimal("75000"),
                        7,
                        1L
                );

        TravelPackageResponseDTO response =
                new TravelPackageResponseDTO(
                        1L,
                        "Paris Premium Holiday",
                        new BigDecimal("75000"),
                        7,
                        1L
                );

        when(travelPackageService.updatePackage(
                any(Long.class),
                any(TravelPackageRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        put("/api/packages/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.packageName")
                        .value("Paris Premium Holiday"))
                .andExpect(jsonPath("$.price")
                        .value(75000))
                .andExpect(jsonPath("$.duration")
                        .value(7))
                .andExpect(jsonPath("$.destinationId")
                        .value(1));
    }


    // UC-05: Delete Travel Package
    @Test
    void deletePackage_shouldReturn204() throws Exception {

        doNothing()
                .when(travelPackageService)
                .deletePackage(1L);

        mockMvc.perform(
                        delete("/api/packages/1")
                )
                .andExpect(status().isNoContent());
    }


    // UC-06: Get Packages By Destination
    @Test
    void getPackagesByDestination_shouldReturn200()
            throws Exception {

        TravelPackageResponseDTO response =
                new TravelPackageResponseDTO(
                        1L,
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        5,
                        1L
                );

        when(travelPackageService
                .getPackagesByDestination(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/packages/destination/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].packageName")
                        .value("Paris Holiday"))
                .andExpect(jsonPath("$[0].price")
                        .value(50000))
                .andExpect(jsonPath("$[0].duration")
                        .value(5))
                .andExpect(jsonPath("$[0].destinationId")
                        .value(1));
    }


    // Validation: Empty Package Name
    @Test
    void createPackage_withEmptyName_shouldReturn400()
            throws Exception {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "",
                        new BigDecimal("50000"),
                        5,
                        1L
                );

        mockMvc.perform(
                        post("/api/packages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isBadRequest());
    }


    // Validation: Negative Price
    @Test
    void createPackage_withNegativePrice_shouldReturn400()
            throws Exception {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "Paris Holiday",
                        new BigDecimal("-500"),
                        5,
                        1L
                );

        mockMvc.perform(
                        post("/api/packages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isBadRequest());
    }


    // Validation: Zero Duration
    @Test
    void createPackage_withZeroDuration_shouldReturn400()
            throws Exception {

        TravelPackageRequestDTO request =
                new TravelPackageRequestDTO(
                        "Paris Holiday",
                        new BigDecimal("50000"),
                        0,
                        1L
                );

        mockMvc.perform(
                        post("/api/packages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isBadRequest());
    }
}