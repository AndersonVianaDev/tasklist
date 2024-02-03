package com.anderson.tasklist.core.shared.exceptions;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}
