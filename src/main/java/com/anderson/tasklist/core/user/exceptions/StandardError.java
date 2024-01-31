package com.anderson.tasklist.core.user.exceptions;

import java.time.LocalDateTime;

public class StandardError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError(LocalDateTime timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
