package com.anderson.tasklist.core.task.services.impl;

import com.anderson.tasklist.core.shared.exceptions.InvalidDataException;
import com.anderson.tasklist.core.shared.exceptions.InvalidDateException;
import com.anderson.tasklist.core.task.dtos.TaskDto;
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
}
