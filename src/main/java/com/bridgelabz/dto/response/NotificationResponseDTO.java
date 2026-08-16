package com.bridgelabz.dto.response;

public class NotificationResponseDTO {

    private Long id;
    private String message;
    private String type;
    private String status;
    private Long userId;

    public NotificationResponseDTO() {
    }

    public NotificationResponseDTO(Long id, String message, String type,
                                   String status, Long userId) {
        this.id = id;
        this.message = message;
        this.type = type;
        this.status = status;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}