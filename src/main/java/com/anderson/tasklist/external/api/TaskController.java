package com.anderson.tasklist.external.api;

import com.anderson.tasklist.core.task.dtos.TaskDto;
import com.anderson.tasklist.core.task.dtos.UpdateTaskDto;
import com.anderson.tasklist.core.task.model.Task;
import com.anderson.tasklist.core.task.services.TaskService;
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
    private final TokenService tokenService;

    public TaskController(TaskService service, TokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }

    @PostMapping("/create")
    @Operation(summary = "create task", method = "POST")
    public ResponseEntity create(@RequestHeader(name = "Authorization") String token, @RequestBody TaskDto taskDto) {
        UUID idUser = this.tokenService.validateToken(token);

        this.service.create(idUser, taskDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("task created");
    }

    @GetMapping("/find/id/{id}")
    @Operation(summary = "find by task id", method = "GET")
    public ResponseEntity findById(@RequestHeader(name = "Authorization") String token, @PathVariable UUID id) {
        UUID idUser = this.tokenService.validateToken(token);

        Task task = this.service.findById(idUser, id);

        TaskDto taskDto = this.service.toTaskDto(task);

        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @DeleteMapping("/delete/id/{id}")
    @Operation(summary = "delete task", method = "DELETE")
    public ResponseEntity delete(@RequestHeader(name = "Authorization") String token, @PathVariable UUID id) {
        UUID idUser = this.tokenService.validateToken(token);

        this.service.delete(idUser, id);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }

    @PatchMapping("/update/id/{id}")
    @Operation(summary = "update task", method = "PATCH")
    public ResponseEntity update(@RequestHeader(name = "Authorization") String token, @PathVariable UUID id, @RequestBody UpdateTaskDto updateTaskDto) {
        UUID idUser = this.tokenService.validateToken(token);

        Task task = this.service.update(idUser, id, updateTaskDto);

        TaskDto taskDto = this.service.toTaskDto(task);

        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @GetMapping("/findAll")
    @Operation(summary = "find tasks by user id", method = "GET")
    public ResponseEntity findAll(@RequestHeader(name = "Authorization") String token) {
        UUID idUser = this.tokenService.validateToken(token);

        List<Task> tasks = this.service.findAll(idUser);

        List<TaskDto> taskDtos = this.service.toTaskDtos(tasks);

        return ResponseEntity.status(HttpStatus.OK).body(taskDtos);
    }

    @GetMapping("/findAllActive")
    @Operation(summary = "find active tasks by user id", method = "GET")
    public ResponseEntity<List<TaskDto>> findAllActive(@RequestHeader(name = "Authorization") String token) {
        UUID idUser = this.tokenService.validateToken(token);

        List<Task> tasks = this.service.findAllActive(idUser);

        List<TaskDto> taskDtos = this.service.toTaskDtos(tasks);

        return ResponseEntity.status(HttpStatus.OK).body(taskDtos);
    }
}
