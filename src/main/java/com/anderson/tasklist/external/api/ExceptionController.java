package com.anderson.tasklist.external.api;

import com.anderson.tasklist.core.shared.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<StandardError> invalidData(InvalidDataException e, HttpServletRequest request) {
        String error = "Forbidden";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> notFound(NotFoundException e, HttpServletRequest request) {
        String error = "Not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<StandardError> invalidDate(InvalidDateException e, HttpServletRequest request) {
        String error = "Invalid date";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<StandardError> invalidUser(InvalidUserException e, HttpServletRequest request) {
        String error = "Invalid user";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<StandardError> invalidToken(InvalidTokenException e, HttpServletRequest request) {
        String error = "Invalid token";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(LocalDateTime.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
