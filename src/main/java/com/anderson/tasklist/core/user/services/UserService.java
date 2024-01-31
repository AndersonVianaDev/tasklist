package com.anderson.tasklist.core.user.services;

import com.anderson.tasklist.core.user.dtos.UserDto;

public interface UserService<E, I> {

    void create(UserDto userDto);

}
