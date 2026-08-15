package com.bridgelabz.controller;

import com.bridgelabz.dto.request.DestinationRequestDTO;
import com.bridgelabz.dto.response.DestinationResponseDTO;
import com.bridgelabz.service.DestinationService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DestinationController.class)
class DestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DestinationService destinationService;


    // UC-01: Create Destination
    @Test
    void createDestination_shouldReturn201() throws Exception {

        DestinationRequestDTO request =
                new DestinationRequestDTO(
                        "Paris",
                        "France",
                        "City of love"
                );

        DestinationResponseDTO response =
                new DestinationResponseDTO(
                        1L,
                        "Paris",
                        "France",
                        "City of love"
                );

        when(destinationService.createDestination(
                any(DestinationRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/destinations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Paris"))
                .andExpect(jsonPath("$.country").value("France"))
                .andExpect(jsonPath("$.description")
                        .value("City of love"));
    }


    // UC-02: Get All Destinations
    @Test
    void getAllDestinations_shouldReturn200() throws Exception {

        DestinationResponseDTO destination =
                new DestinationResponseDTO(
                        1L,
                        "Paris",
                        "France",
                        "City of love"
                );

        when(destinationService.getAllDestinations())
                .thenReturn(List.of(destination));

        mockMvc.perform(
                        get("/api/destinations")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Paris"))
                .andExpect(jsonPath("$[0].country")
                        .value("France"))
                .andExpect(jsonPath("$[0].description")
                        .value("City of love"));
    }


    // UC-03: Get Destination By ID
    @Test
    void getDestinationById_shouldReturn200() throws Exception {

        DestinationResponseDTO response =
                new DestinationResponseDTO(
                        1L,
                        "Paris",
                        "France",
                        "City of love"
                );

        when(destinationService.getDestinationById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/destinations/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Paris"))
                .andExpect(jsonPath("$.country")
                        .value("France"))
                .andExpect(jsonPath("$.description")
                        .value("City of love"));
    }


    // Validation: Empty Name
    @Test
    void createDestination_withEmptyName_shouldReturn400()
            throws Exception {

        DestinationRequestDTO request =
                new DestinationRequestDTO(
                        "",
                        "France",
                        "City of love"
                );

        mockMvc.perform(
                        post("/api/destinations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isBadRequest());
    }


    // Validation: Empty Country
    @Test
    void createDestination_withEmptyCountry_shouldReturn400()
            throws Exception {

        DestinationRequestDTO request =
                new DestinationRequestDTO(
                        "Paris",
                        "",
                        "City of love"
                );

        mockMvc.perform(
                        post("/api/destinations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isBadRequest());
    }
}