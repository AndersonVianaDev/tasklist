package com.anderson.tasklist.core.user.services;

import com.anderson.tasklist.core.user.model.User;

import java.util.UUID;

public interface TokenGenerator {

    String generateToken(User user);

    UUID validateToken(String token);

    void verifyToken(String token, UUID id);
}
