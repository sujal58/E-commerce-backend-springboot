package com.sujal.Ecommerce.DTO.Response;

import jakarta.servlet.http.HttpServlet;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse {

    private HttpStatus status;
    private String message;
    private Object data;
    private String path;
    private LocalDateTime timestamp;



    // Constructor for successful response with data
    public ApiResponse(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // Constructor for successful response without data
    public ApiResponse(HttpStatus status, String message) {
        this(status, message, null);
    }

    // Constructor for failure response with message
    public ApiResponse(HttpStatus status, String message, String error, String path) {
        this.status = status;
        this.message = message;
        this.data = error;
        this.path = path;// Assuming error is a String message
        this.timestamp = LocalDateTime.now();

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // Getters and Setters
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Static helper method for a successful response with data
    public static ApiResponse success(HttpStatus status, String message, Object data) {
        return new ApiResponse(status, message, data);
    }

    // Static helper method for a successful response without data
    public static ApiResponse success(HttpStatus status, String message) {
        return new ApiResponse(status, message);
    }

    // Static helper method for an error response
    public static ApiResponse error(HttpStatus status, String message, String error, String path) {
        return new ApiResponse(status, message, error, path);
    }
}
