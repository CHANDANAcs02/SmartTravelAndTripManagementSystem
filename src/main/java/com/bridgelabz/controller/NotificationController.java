package com.bridgelabz.controller;

import com.bridgelabz.dto.response.NotificationResponseDTO;
import com.bridgelabz.exception.NotificationNotFoundException;
import com.bridgelabz.service.NotificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Endpoint 19: Get all notifications for a particular user.
    @GetMapping("/users/{id}/notifications")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUser(
            @PathVariable("id") Long userId) {

        List<NotificationResponseDTO> notifications =
                notificationService.getNotificationsByUser(userId);

        return ResponseEntity.ok(notifications);
    }

    //Endpoint 20: Mark Notification as read
    @PostMapping("/notification/{id}/read")
    public ResponseEntity<NotificationResponseDTO> readNotification(@PathVariable Long id) throws NotificationNotFoundException {
       return ResponseEntity.ok( notificationService.markAsRead(id));
    }

}
