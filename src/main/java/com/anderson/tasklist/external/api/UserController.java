package com.anderson.tasklist.external.api;

import com.anderson.tasklist.core.user.dtos.LoginDto;
import com.anderson.tasklist.core.user.dtos.UserDto;
import com.anderson.tasklist.core.user.services.UserService;
import com.anderson.tasklist.core.user.exceptions.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        this.service.create(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        var usernamepassword = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());

        try {
            var auth = authenticationManager.authenticate(usernamepassword);
        } catch (AuthenticationException e) {
            throw new InvalidDataException("invalid email or password");
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
