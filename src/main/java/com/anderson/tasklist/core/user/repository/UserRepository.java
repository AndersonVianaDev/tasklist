package com.anderson.tasklist.core.user.repository;

import com.anderson.tasklist.core.user.model.User;

public interface UserRepository {
    void create(User user);

    User findByEmail(String email);
}
