package com.anderson.tasklist.core.user.repository;

import com.anderson.tasklist.core.user.model.User;

import java.util.UUID;

public interface UserRepository {
    void create(User user);

    User findById(UUID id);

    User findByEmail(String email);

    User save(User user);

    void delete(User user);
}
