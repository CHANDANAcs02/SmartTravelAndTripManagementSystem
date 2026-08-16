package com.bridgelabz.service;

import com.bridgelabz.dto.request.UserRequestDTO;
import com.bridgelabz.dto.response.UserResponseDTO;
import com.bridgelabz.exception.DuplicateEmailException;
import com.bridgelabz.exception.UserNotFoundException;
import com.bridgelabz.model.User;
import com.bridgelabz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // Register a new user
    public UserResponseDTO createUser(UserRequestDTO request) {

        // Check whether email is already registered
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(
                    "Email already exists: " + request.getEmail()
            );
        }

        // Convert Request DTO to User Entity
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        // Save user into database
        User savedUser = userRepository.save(user);

        // Convert Entity to Response DTO
        return convertToResponseDTO(savedUser);
    }


    // Get user by ID
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        return convertToResponseDTO(user);
    }


    // Get all users
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }


    // Update user
    public UserResponseDTO updateUser(
            Long id,
            UserRequestDTO request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        // Check if the new email belongs to another user
        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateEmailException(
                    "Email already exists: " + request.getEmail()
            );
        }

        // Update user details
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);

        return convertToResponseDTO(updatedUser);
    }


    //Delete user by user id
    public String deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
        return user.getName()+" is Removed";
    }





    // Convert User Entity to UserResponseDTO
    private UserResponseDTO convertToResponseDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }
}