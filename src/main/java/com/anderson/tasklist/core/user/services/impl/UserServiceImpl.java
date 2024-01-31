package com.anderson.tasklist.core.user.services.impl;

import com.anderson.tasklist.core.user.dtos.UserDto;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.repository.UserRepository;
import com.anderson.tasklist.core.user.services.PasswordCryptography;
import com.anderson.tasklist.core.user.services.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordCryptography passwordCryptography;

    public UserServiceImpl(UserRepository repository, PasswordCryptography passwordCryptography) {
        this.repository = repository;
        this.passwordCryptography = passwordCryptography;
    }

    @Override
    public void create(UserDto userDto) {
        User user = new User(userDto);
        String password = user.getPassword();
        password = this.passwordCryptography.encode(password);
        user.setPassword(password);

        this.repository.create(user);
    }

}
