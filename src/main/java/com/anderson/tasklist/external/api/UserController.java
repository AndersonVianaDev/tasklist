package com.anderson.tasklist.external.api;

import com.anderson.tasklist.core.user.dtos.*;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.services.UserService;
import com.anderson.tasklist.external.auth.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    private final TokenService tokenService;

    public UserController(UserService service, TokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        this.service.create(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        LoginResponseDto loginResponse = this.service.login(loginDto);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<UserResponseDto> findById(@RequestHeader(name = "Authorization") String token, @PathVariable UUID id) {
        User user = this.service.findById(id);

        token = token.replace("Bearer ", "");

        this.tokenService.verifyToken(token, user.getEmail());

        UserResponseDto userDto = new UserResponseDto(user.getName(), user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<UserResponseDto> findByEmail(@RequestHeader(name = "Authorization") String token, @PathVariable String email) {
        User user = this.service.findByEmail(email);

        token = token.replace("Bearer ", "");

        this.tokenService.verifyToken(token, email);

        UserResponseDto userDto = new UserResponseDto(user.getName(), user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PatchMapping("/update/id/{id}")
    public ResponseEntity update(@RequestHeader(name = "Authorization") String token, @PathVariable UUID id, @RequestBody UpdateDto updateDto) {
        User user = this.service.update(id, updateDto);

        token = token.replace("Bearer ", "");

        this.tokenService.verifyToken(token, user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body("Updated password");
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity delete(@RequestHeader(name =  "Authorization") String token, @PathVariable UUID id) {
        User user = this.service.findById(id);

        token = token.replace("Bearer ", "");

        this.tokenService.verifyToken(token, user.getEmail());

        this.service.delete(user);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }
}
