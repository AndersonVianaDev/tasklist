package com.anderson.tasklist.external.api;

import com.anderson.tasklist.adapter.entities.UserEntityAdapter;
import com.anderson.tasklist.core.user.dtos.LoginDto;
import com.anderson.tasklist.core.user.dtos.LoginResponseDto;
import com.anderson.tasklist.core.user.dtos.UserDto;
import com.anderson.tasklist.core.user.dtos.UserResponseDto;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.services.TokenGenerator;
import com.anderson.tasklist.core.user.services.UserService;
import com.anderson.tasklist.core.shared.exceptions.InvalidDataException;
import com.anderson.tasklist.external.auth.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        var usernamepassword = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());

        try {
            var auth = authenticationManager.authenticate(usernamepassword);

            LoginResponseDto loginResponse = this.service.login(loginDto);

            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } catch (AuthenticationException e) {
            throw new InvalidDataException("invalid email or password");
        }
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        User user = this.service.findById(id);
        UserResponseDto userDto = new UserResponseDto(user.getName(), user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<UserResponseDto> findByEmail(@PathVariable String email) {
        User user = this.service.findByEmail(email);
        UserResponseDto userDto = new UserResponseDto(user.getName(), user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
