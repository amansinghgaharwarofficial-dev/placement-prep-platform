package com.example.demo.dto;

import java.time.LocalDateTime;

public class ApiResponse {

    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ApiResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}