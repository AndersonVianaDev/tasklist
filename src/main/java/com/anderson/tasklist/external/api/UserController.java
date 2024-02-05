package com.anderson.tasklist.external.api;

import com.anderson.tasklist.core.user.dtos.*;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.services.UserService;
import com.anderson.tasklist.external.auth.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/users")
@Tag(name = "tasklist-api")
public class UserController {

    private final UserService service;
    private final TokenService tokenService;

    public UserController(UserService service, TokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    @Operation(summary = "register user", method = "POST")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        this.service.create(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(summary = "login user", method = "POST")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        LoginResponseDto loginResponse = this.service.login(loginDto);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @GetMapping("/find/id/{id}")
    @Operation(summary = "find by user id", method = "GET")
    public ResponseEntity<UserResponseDto> findById(@RequestHeader(name = "Authorization") String token) {
        UUID idUser = this.tokenService.validateToken(token);

        User user = this.service.findById(idUser);

        UserResponseDto userDto = new UserResponseDto(user.getName(), user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/find/email/{email}")
    @Operation(summary = "find by user email", method = "GET")
    public ResponseEntity<UserResponseDto> findByEmail(@RequestHeader(name = "Authorization") String token, @PathVariable String email) {
        User user = this.service.findByEmail(email);

        this.tokenService.verifyToken(token, user.getId());

        UserResponseDto userDto = new UserResponseDto(user.getName(), user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PatchMapping("/update/id/{id}")
    @Operation(summary = "update password", method = "PATCH")
    public ResponseEntity update(@RequestHeader(name = "Authorization") String token, @RequestBody UpdateDto updateDto) {
        UUID id = this.tokenService.validateToken(token);

        this.service.update(id, updateDto);

        return ResponseEntity.status(HttpStatus.OK).body("Updated password");
    }

    @DeleteMapping("/delete/id/{id}")
    @Operation(summary = "delete user", method = "DELETE")
    public ResponseEntity delete(@RequestHeader(name =  "Authorization") String token, @PathVariable UUID id) {
        User user = this.service.findById(id);

        this.tokenService.verifyToken(token, user.getId());

        this.service.delete(user);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }
}
