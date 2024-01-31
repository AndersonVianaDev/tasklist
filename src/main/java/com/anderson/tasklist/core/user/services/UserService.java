package com.anderson.tasklist.core.user.services;

import com.anderson.tasklist.core.user.dtos.UserDto;
import com.anderson.tasklist.core.user.model.User;

public interface UserService {

    void create(UserDto userDto);

    User findByEmail(String email);

}
