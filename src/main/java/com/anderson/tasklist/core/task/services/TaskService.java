package com.anderson.tasklist.core.task.services;

import com.anderson.tasklist.core.task.dtos.TaskDto;
import com.anderson.tasklist.core.task.model.Task;

import java.util.UUID;

public interface TaskService {

    void create(UUID idUser, TaskDto taskDto);

    Task findById(UUID idUser, UUID id);

    TaskDto toTaskDto(Task task);
}
