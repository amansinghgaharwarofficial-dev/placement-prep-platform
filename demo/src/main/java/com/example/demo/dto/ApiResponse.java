package com.example.demo.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private String message;
    private int status;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() { return message; }

    public int getStatus() { return status; }

    public T getData() { return data; }

    public LocalDateTime getTimestamp() { return timestamp; }
}