package com.anderson.tasklist.core.task.services;

import com.anderson.tasklist.core.task.dtos.TaskDto;

import java.util.UUID;

public interface TaskService {

    void create(UUID idUser, TaskDto taskDto);
}
