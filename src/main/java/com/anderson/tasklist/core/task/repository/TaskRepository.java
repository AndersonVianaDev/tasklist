package com.anderson.tasklist.core.task.repository;

import com.anderson.tasklist.core.task.model.Task;

import java.util.UUID;

public interface TaskRepository {

    void create(Task task);

    Task findById(UUID id);

    void delete(Task task);

    Task update(Task task);
}
