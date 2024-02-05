package com.anderson.tasklist.core.task.repository;

import com.anderson.tasklist.core.task.model.Task;
import com.anderson.tasklist.core.user.model.User;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {

    void create(Task task);

    Task findById(UUID id);

    void delete(Task task);

    Task update(Task task);

    List<Task> findAll(User user);

    List<Task> findAllActive(User user);
}
