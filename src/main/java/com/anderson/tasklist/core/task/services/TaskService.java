package com.anderson.tasklist.core.task.services;

import com.anderson.tasklist.core.task.dtos.TaskDto;
import com.anderson.tasklist.core.task.dtos.UpdateTaskDto;
import com.anderson.tasklist.core.task.model.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    void create(UUID idUser, TaskDto taskDto);

    Task findById(UUID idUser, UUID id);

    void delete(UUID idUser, UUID id);

    Task update(UUID idUser, UUID id, UpdateTaskDto updateTaskDto);

    List<Task> findAll(UUID idUser);

    TaskDto toTaskDto(Task task);

    List<TaskDto> toTaskDtos(List<Task> tasks);
}
