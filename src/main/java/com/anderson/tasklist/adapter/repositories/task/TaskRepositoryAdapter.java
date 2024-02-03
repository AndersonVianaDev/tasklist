package com.anderson.tasklist.adapter.repositories.task;

import com.anderson.tasklist.adapter.entities.TaskEntityAdapter;
import com.anderson.tasklist.core.task.model.Task;
import com.anderson.tasklist.core.task.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TaskRepositoryAdapter implements TaskRepository {

    private final SpringTaskRepository repository;

    public TaskRepositoryAdapter(SpringTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Task task) {
        TaskEntityAdapter taskEntity = new TaskEntityAdapter(task);

        this.repository.save(taskEntity);
    }

    @Override
    public Task findById(UUID id) {
        Optional<TaskEntityAdapter> taskEntity = this.repository.findById(id);

        if(taskEntity.isPresent()) return taskEntity.get().toTask();

        return null;
    }
}
