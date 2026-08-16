package com.bridgelabz.service;

import com.bridgelabz.dto.response.NotificationResponseDTO;
import com.bridgelabz.exception.NotificationNotFoundException;
import com.bridgelabz.exception.UserNotFoundException;
import com.bridgelabz.model.Notification;
import com.bridgelabz.model.User;
import com.bridgelabz.repository.NotificationRepository;
import com.bridgelabz.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    //Create notification when review saved
    public NotificationResponseDTO createNotification(User user, String message, String type) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        notification.setStatus("UNREAD");
        notification.setType(type);
        return convertToResponse(notificationRepository.save(notification));
    }

    // Retrieves all notifications belonging to the requested user.
    public List<NotificationResponseDTO> getNotificationsByUser(Long userId) {

        // Check whether the user exists before retrieving notifications.
        userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + userId
                        ));

        // Find all notifications associated with this user.
        return notificationRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    //Notification mark as read
    public NotificationResponseDTO markAsRead(Long id) throws NotificationNotFoundException {

        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException("Notification id not found"));
        notification.setStatus("READ");
        return convertToResponse(notificationRepository.save(notification));
    }

    // Converts the Notification entity into a response DTO.
    private NotificationResponseDTO convertToResponse(Notification notification) {

        return new NotificationResponseDTO(
                notification.getId(),
                notification.getMessage(),
                notification.getType(),
                notification.getStatus(),
                notification.getUser().getId()
        );
    }




}