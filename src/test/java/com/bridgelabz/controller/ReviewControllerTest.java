package com.bridgelabz.controller;

import com.bridgelabz.dto.request.ReviewRequestDTO;
import com.bridgelabz.dto.response.ReviewResponseDTO;
import com.bridgelabz.service.ReviewService;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReviewService reviewService;


    // Endpoint 16: POST /api/reviews
    @Test
    void createReview_shouldReturn201() throws Exception {

        ReviewRequestDTO request =
                new ReviewRequestDTO(
                        5,
                        "Excellent trip",
                        1L,
                        10L
                );

        ReviewResponseDTO response =
                new ReviewResponseDTO(
                        1L,
                        5,
                        "Excellent trip",
                        1L,
                        10L
                );

        when(reviewService.createReview(any(ReviewRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment")
                        .value("Excellent trip"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.travelPackageId").value(10));
    }


    // Endpoint 17: GET /api/packages/{id}/reviews
    @Test
    void getReviewsByPackage_shouldReturn200() throws Exception {

        ReviewResponseDTO response =
                new ReviewResponseDTO(
                        1L,
                        5,
                        "Excellent trip",
                        1L,
                        10L
                );

        when(reviewService.getReviewsByPackage(10L))
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/packages/10/reviews")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment")
                        .value("Excellent trip"))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].travelPackageId").value(10));
    }


    // Endpoint 18: PUT /api/reviews/{id}
    @Test
    void updateReview_shouldReturn200() throws Exception {

        ReviewRequestDTO request =
                new ReviewRequestDTO(
                        4,
                        "Good trip",
                        1L,
                        10L
                );

        ReviewResponseDTO response =
                new ReviewResponseDTO(
                        1L,
                        4,
                        "Good trip",
                        1L,
                        10L
                );

        when(reviewService.updateReview(
                any(Long.class),
                any(ReviewRequestDTO.class)
        )).thenReturn(response);

        mockMvc.perform(
                        put("/api/reviews/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment")
                        .value("Good trip"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.travelPackageId").value(10));
    }
}