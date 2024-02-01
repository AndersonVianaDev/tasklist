package com.anderson.tasklist.core.user.services.impl;

import com.anderson.tasklist.core.shared.exceptions.InvalidDataException;
import com.anderson.tasklist.core.shared.exceptions.NotFoundException;
import com.anderson.tasklist.core.user.dtos.LoginDto;
import com.anderson.tasklist.core.user.dtos.LoginResponseDto;
import com.anderson.tasklist.core.user.dtos.UserDto;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.repository.UserRepository;
import com.anderson.tasklist.core.user.services.PasswordCryptography;
import com.anderson.tasklist.core.user.services.TokenGenerator;
import com.anderson.tasklist.core.user.services.UserService;
import com.anderson.tasklist.external.auth.TokenService;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordCryptography passwordCryptography;
    private final TokenGenerator tokenGenerator;

    public UserServiceImpl(UserRepository repository, PasswordCryptography passwordCryptography, TokenGenerator tokenGenerator) {
        this.repository = repository;
        this.passwordCryptography = passwordCryptography;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public void create(UserDto userDto) {
        User user = new User(userDto);
        String password = user.getPassword();
        password = this.passwordCryptography.encode(password);
        user.setPassword(password);

        this.repository.create(user);
    }

    @Override
    public LoginResponseDto login(LoginDto userLogin) {
        User user = this.findByEmail(userLogin.email());

        if(!this.passwordCryptography.toCompare(user.getPassword(), userLogin.password())){
            throw new InvalidDataException("invalid email or password");
        }

        String token = this.tokenGenerator.generateToken(user);

        LoginResponseDto loginResponse = new LoginResponseDto(token);

        return loginResponse;
    }


    @Override
    public User findById(UUID id) {
        if(id == null) {
            throw new InvalidDataException("Required id");
        }

        User user = this.repository.findById(id);

        if(user == null) {
            throw new NotFoundException("User with id "+ id +" not found !");
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        if(email == null) {
            throw new InvalidDataException("Required email");
        }

        User user = this.repository.findByEmail(email);

        if(user == null) {
            throw new NotFoundException("User with email "+ email +" not found !");
        }

        return user;
    }


}
