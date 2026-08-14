package com.bridgelabz.controller;

import com.bridgelabz.dto.request.UserRequestDTO;
import com.bridgelabz.dto.response.UserResponseDTO;
import com.bridgelabz.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    // Register user
    // POST /api/users
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO request) {

        UserResponseDTO response =
                userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // Get all users
    // GET /api/users
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        List<UserResponseDTO> users =
                userService.getAllUsers();

        return ResponseEntity.ok(users);
    }


    // Get user by ID
    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long id) {

        UserResponseDTO response =
                userService.getUserById(id);

        return ResponseEntity.ok(response);
    }


    //  Update user
    // PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO request) {

        UserResponseDTO response =
                userService.updateUser(id, request);

        return ResponseEntity.ok(response);
    }



}