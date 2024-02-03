package com.anderson.tasklist.core.task.repository;

import com.anderson.tasklist.core.task.model.Task;

public interface TaskRepository {

    void create(Task task);
}
