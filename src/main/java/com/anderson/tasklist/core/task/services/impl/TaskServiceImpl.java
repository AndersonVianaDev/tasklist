package com.anderson.tasklist.core.task.services.impl;

import com.anderson.tasklist.core.shared.exceptions.InvalidDataException;
import com.anderson.tasklist.core.shared.exceptions.InvalidDateException;
import com.anderson.tasklist.core.shared.exceptions.InvalidUserException;
import com.anderson.tasklist.core.shared.exceptions.NotFoundException;
import com.anderson.tasklist.core.task.dtos.TaskDto;
import com.anderson.tasklist.core.task.dtos.UpdateTaskDto;
import com.anderson.tasklist.core.task.model.Task;
import com.anderson.tasklist.core.task.repository.TaskRepository;
import com.anderson.tasklist.core.task.services.TaskService;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public void create(UUID idUser, TaskDto taskDto) {
        if(idUser == null || taskDto == null) {
            throw new InvalidDataException("required data !");
        }

        if(taskDto.expirationDate().isBefore(LocalDate.now()) || taskDto.expirationDate().equals(LocalDate.now())) {
            throw new InvalidDateException("expiration date must be after today's date !");
        }

        Task task = new Task(taskDto);

        User user = this.userService.findById(idUser);

        task.setUser(user);

        this.repository.create(task);
    }

    @Override
    public Task findById(UUID idUser, UUID id) {
        if(id == null) throw new InvalidDataException("Required id !");

        Task task = this.repository.findById(id);

        if(task == null) throw new NotFoundException("Task with id "+ id +" not found !");

        if(task.getUser().getId() == idUser) throw new InvalidUserException("the task does not belong to the user !");

        return task;
    }

    @Override
    public void delete(UUID idUser, UUID id) {
        Task task = findById(idUser, id);

        this.repository.delete(task);
    }

    @Override
    public Task update(UUID idUser, UUID id, UpdateTaskDto updateTaskDto) {
        if(idUser == null || id == null) throw new InvalidDataException("Required data !");
        Task task = findById(idUser, id);

        task.setConcluded(updateTaskDto.concluded());

        return this.repository.update(task);
    }

    @Override
    public List<Task> findAll(UUID idUser) {
        if(idUser == null) throw new InvalidDataException("Required id !");

        User user = this.userService.findById(idUser);

        List<Task> tasks = this.repository.findAll(user);

        return tasks;
    }

    @Override
    public List<Task> findAllActive(UUID idUser) {
        if(idUser == null) throw new InvalidDataException("Required id !");

        User user = this.userService.findById(idUser);

        List<Task> tasks = this.repository.findAllActive(user);

        return tasks;
    }

    @Override
    public TaskDto toTaskDto(Task task) {
        return new TaskDto(task.getName(), task.getConcluded(), task.getExpirationDate());
    }

    @Override
    public List<TaskDto> toTaskDtos(List<Task> tasks) {
        List<TaskDto> taskDtos = tasks.stream().map(t -> toTaskDto(t)).toList();

        return taskDtos;
    }
}
