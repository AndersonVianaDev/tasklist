package com.anderson.tasklist.external.api;

import com.anderson.tasklist.core.task.dtos.TaskDto;
import com.anderson.tasklist.core.task.dtos.UpdateTaskDto;
import com.anderson.tasklist.core.task.model.Task;
import com.anderson.tasklist.core.task.services.TaskService;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.services.UserService;
import com.anderson.tasklist.external.auth.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/tasks")
@Tag(name = "tasklist-api")
public class TaskController {

    private final TaskService service;
    private final UserService userService;
    private final TokenService tokenService;

    public TaskController(TaskService service, UserService userService, TokenService tokenService) {
        this.service = service;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/create/id/{idUser}")
    @Operation(summary = "create task", method = "POST")
    public ResponseEntity create(@RequestHeader(name = "Authorization") String token, @PathVariable UUID idUser, @RequestBody TaskDto taskDto) {
        User user = this.userService.findById(idUser);

        this.tokenService.verifyToken(token, user.getEmail());

        this.service.create(idUser, taskDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("task created");
    }

    @GetMapping("/find/id/{id}")
    @Operation(summary = "find by task id", method = "GET")
    public ResponseEntity findById(@RequestHeader(name = "Authorization") String token, @PathVariable UUID id) {
        String email = this.tokenService.validateToken(token);

        User user = this.userService.findByEmail(email);

        Task task = this.service.findById(user.getId(), id);

        TaskDto taskDto = this.service.toTaskDto(task);

        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @DeleteMapping("/delete/id/{id}")
    @Operation(summary = "delete task", method = "DELETE")
    public ResponseEntity delete(@RequestHeader(name = "Authorization") String token, @PathVariable UUID id) {
        String email = this.tokenService.validateToken(token);

        User user = this.userService.findByEmail(email);

        this.service.delete(user.getId(), id);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }

    @PatchMapping("/update/id/{id}")
    @Operation(summary = "update task", method = "PATCH")
    public ResponseEntity update(@RequestHeader(name = "Authorization") String token, @PathVariable UUID id, @RequestBody UpdateTaskDto updateTaskDto) {
        String email = this.tokenService.validateToken(token);

        User user = this.userService.findByEmail(email);

        Task task = this.service.update(user.getId(), id, updateTaskDto);

        TaskDto taskDto = this.service.toTaskDto(task);

        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @GetMapping("/findAll/id/{idUser}")
    @Operation(summary = "find tasks by user id", method = "GET")
    public ResponseEntity findAll(@RequestHeader(name = "Authorization") String token, @PathVariable UUID idUser) {
        User user = this.userService.findById(idUser);

        this.tokenService.verifyToken(token, user.getEmail());

        List<Task> tasks = this.service.findAll(idUser);

        List<TaskDto> taskDtos = this.service.toTaskDtos(tasks);

        return ResponseEntity.status(HttpStatus.OK).body(taskDtos);
    }

    @GetMapping("/findAllActive/id/{idUser}")
    @Operation(summary = "find active tasks by user id", method = "GET")
    public ResponseEntity<List<TaskDto>> findAllActive(@RequestHeader(name = "Authorization") String token, @PathVariable UUID idUser) {
        User user = this.userService.findById(idUser);

        this.tokenService.verifyToken(token, user.getEmail());

        List<Task> tasks = this.service.findAllActive(idUser);

        List<TaskDto> taskDtos = this.service.toTaskDtos(tasks);

        return ResponseEntity.status(HttpStatus.OK).body(taskDtos);
    }
}
