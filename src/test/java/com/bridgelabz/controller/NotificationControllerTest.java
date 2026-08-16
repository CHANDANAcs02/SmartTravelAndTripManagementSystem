package com.bridgelabz.controller;

import com.bridgelabz.dto.response.NotificationResponseDTO;
import com.bridgelabz.service.NotificationService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;


    // Endpoint 19: GET /api/users/{id}/notifications
    @Test
    void getNotificationsByUser_shouldReturn200() throws Exception {

        NotificationResponseDTO response =
                new NotificationResponseDTO(
                        1L,
                        "Your booking has been confirmed",
                        "BOOKING",
                        "UNREAD",
                        1L
                );

        when(notificationService.getNotificationsByUser(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/users/1/notifications")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].message")
                        .value("Your booking has been confirmed"))
                .andExpect(jsonPath("$[0].type")
                        .value("BOOKING"))
                .andExpect(jsonPath("$[0].status")
                        .value("UNREAD"))
                .andExpect(jsonPath("$[0].userId").value(1));
    }
}