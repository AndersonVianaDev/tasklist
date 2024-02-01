package com.anderson.tasklist.core.user.services;

import com.anderson.tasklist.core.user.dtos.LoginDto;
import com.anderson.tasklist.core.user.dtos.LoginResponseDto;
import com.anderson.tasklist.core.user.dtos.UserDto;
import com.anderson.tasklist.core.user.model.User;

import java.util.UUID;

public interface UserService {

    void create(UserDto userDto);

    LoginResponseDto login(LoginDto userLogin);

    User findById(UUID id);

    User findByEmail(String email);

}
