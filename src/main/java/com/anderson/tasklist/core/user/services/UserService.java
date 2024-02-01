package com.anderson.tasklist.core.user.services;

import com.anderson.tasklist.core.user.dtos.*;
import com.anderson.tasklist.core.user.model.User;

import java.util.UUID;

public interface UserService {

    void create(UserDto userDto);

    LoginResponseDto login(LoginDto userLogin);

    User findById(UUID id);

    User findByEmail(String email);

    User update(UUID id, UpdateDto updateDto);

    void delete(User user);

}
