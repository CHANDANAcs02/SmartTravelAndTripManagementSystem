package com.bridgelabz.controller;

import com.bridgelabz.dto.request.UserRequestDTO;
import com.bridgelabz.dto.response.UserResponseDTO;
import com.bridgelabz.exception.DuplicateEmailException;
import com.bridgelabz.exception.UserNotFoundException;
import com.bridgelabz.service.UserService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Fake UserService
    @MockitoBean
    private UserService userService;


    // UC-01: Create User
    @Test
    void createUser_shouldReturn201() throws Exception {

        UserRequestDTO request = new UserRequestDTO(
                "Chandana",
                "chandana@gmail.com",
                "9876543210",
                "secret123",
                "USER"
        );

        UserResponseDTO response = new UserResponseDTO(
                1L,
                "Chandana",
                "chandana@gmail.com",
                "9876543210",
                "USER"
        );

        when(userService.createUser(any(UserRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Chandana"))
                .andExpect(jsonPath("$.email")
                        .value("chandana@gmail.com"));
    }


    // UC-02: Get User by ID
    @Test
    void getUserById_shouldReturn200() throws Exception {

        UserResponseDTO response = new UserResponseDTO(
                1L,
                "Chandana",
                "chandana@gmail.com",
                "9876543210",
                "USER"
        );

        when(userService.getUserById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/users/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Chandana"))
                .andExpect(jsonPath("$.email")
                        .value("chandana@gmail.com"));
    }


    // UC-02: User not found
    @Test
    void getUserById_shouldReturn404() throws Exception {

        when(userService.getUserById(999L))
                .thenThrow(
                        new UserNotFoundException(
                                "User not found with id: 999"
                        )
                );

        mockMvc.perform(
                        get("/api/users/999")
                )
                .andExpect(status().isNotFound());
    }


    // UC-03: Get All Users
    @Test
    void getAllUsers_shouldReturn200() throws Exception {

        UserResponseDTO user = new UserResponseDTO(
                1L,
                "Chandana",
                "chandana@gmail.com",
                "9876543210",
                "USER"
        );

        when(userService.getAllUsers())
                .thenReturn(List.of(user));

        mockMvc.perform(
                        get("/api/users")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Chandana"))
                .andExpect(jsonPath("$[0].email")
                        .value("chandana@gmail.com"));
    }


    // UC-04: Update User
    @Test
    void updateUser_shouldReturn200() throws Exception {

        UserRequestDTO request = new UserRequestDTO(
                "Chandana Updated",
                "chandana@gmail.com",
                "9999999999",
                "newpassword",
                "USER"
        );

        UserResponseDTO response = new UserResponseDTO(
                1L,
                "Chandana Updated",
                "chandana@gmail.com",
                "9999999999",
                "USER"
        );

        when(userService.updateUser(
                any(Long.class),
                any(UserRequestDTO.class)
        )).thenReturn(response);

        mockMvc.perform(
                        put("/api/users/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Chandana Updated"))
                .andExpect(jsonPath("$.phone")
                        .value("9999999999"));
    }



    // Validation: Invalid Email
    @Test
    void createUser_withInvalidEmail_shouldReturn400()
            throws Exception {

        UserRequestDTO request = new UserRequestDTO(
                "Chandana",
                "wrong-email",
                "9876543210",
                "secret123",
                "USER"
        );

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isBadRequest());
    }


    // Duplicate Email
    @Test
    void createUser_withDuplicateEmail_shouldReturn409()
            throws Exception {

        UserRequestDTO request = new UserRequestDTO(
                "Chandana",
                "chandana@gmail.com",
                "9876543210",
                "secret123",
                "USER"
        );

        when(userService.createUser(any(UserRequestDTO.class)))
                .thenThrow(
                        new DuplicateEmailException(
                                "Email already exists"
                        )
                );

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isConflict());
    }
}