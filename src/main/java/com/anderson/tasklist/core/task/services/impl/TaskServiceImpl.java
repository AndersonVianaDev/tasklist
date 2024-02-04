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

import java.time.LocalDate;
import java.util.UUID;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(UUID idUser, TaskDto taskDto) {
        if(idUser == null || taskDto == null) {
            throw new InvalidDataException("required data !");
        }

        if(taskDto.expirationDate().isBefore(LocalDate.now()) || taskDto.expirationDate().equals(LocalDate.now())) {
            throw new InvalidDateException("expiration date must be after today's date !");
        }

        Task task = new Task(idUser, taskDto);

        this.repository.create(task);
    }

    @Override
    public Task findById(UUID idUser, UUID id) {
        if(id == null) throw new InvalidDataException("Required id !");

        Task task = this.repository.findById(id);

        if(task == null) throw new NotFoundException("Task with id "+ id +" not found !");

        if(task.getIdUser() == idUser) throw new InvalidUserException("the task does not belong to the user !");

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
    public TaskDto toTaskDto(Task task) {
        return new TaskDto(task.getName(), task.getConcluded(), task.getExpirationDate());
    }
}
