package com.anderson.tasklist.core.user.services;

import com.anderson.tasklist.core.user.model.User;

public interface TokenGenerator {

    String generateToken(User user);

    String validateToken(String token);

    void verifyToken(String token, String email);
}
