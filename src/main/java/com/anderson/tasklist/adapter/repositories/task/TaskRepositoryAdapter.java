package com.anderson.tasklist.adapter.repositories.task;

import com.anderson.tasklist.adapter.entities.TaskEntityAdapter;
import com.anderson.tasklist.adapter.entities.UserEntityAdapter;
import com.anderson.tasklist.adapter.repositories.user.SpringUserRepository;
import com.anderson.tasklist.core.shared.exceptions.NotFoundException;
import com.anderson.tasklist.core.task.model.Task;
import com.anderson.tasklist.core.task.repository.TaskRepository;
import com.anderson.tasklist.core.user.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskRepositoryAdapter implements TaskRepository {

    private final SpringTaskRepository repository;
    private final SpringUserRepository userRepository;

    public TaskRepositoryAdapter(SpringTaskRepository repository, SpringUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(Task task) {
        UUID idUser = task.getUser().getId();
        UserEntityAdapter userExisting = this.userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("User with id "+ idUser +" not found !"));
        TaskEntityAdapter taskEntity = new TaskEntityAdapter(task);

        taskEntity.setUser(userExisting);

        this.repository.save(taskEntity);
    }

    @Override
    public Task findById(UUID id) {
        Optional<TaskEntityAdapter> taskEntity = this.repository.findById(id);

        if(taskEntity.isPresent()) return taskEntity.get().toTask();

        return null;
    }

    @Override
    public void delete(Task task) {
        UUID id = task.getId();

        TaskEntityAdapter taskEntity = this.repository.findById(id).orElseThrow(() -> new NotFoundException("Task with id "+ id +" not found !"));

        this.repository.delete(taskEntity);
    }

    @Override
    public Task update(Task task) {
        UUID id = task.getId();

        TaskEntityAdapter taskEntity = this.repository.findById(id).orElseThrow(() -> new NotFoundException("Task with id "+ id +" not found !"));

        taskEntity.setConcluded(task.getConcluded());

        return taskEntity.toTask();
    }

    @Override
    public List<Task> findAll(User user) {
        List<TaskEntityAdapter> taskEntityList = this.repository.findAll(user.getId());

        if(!taskEntityList.isEmpty()) {
            List<Task> tasks = taskEntityList.stream().map(t -> t.toTask()).toList();
            return tasks;
        }

        return null;
    }

    @Override
    public List<Task> findAllActive(User user) {
        List<TaskEntityAdapter> taskEntityList = this.repository.findAllActive(user.getId());

        if(!taskEntityList.isEmpty()) {
            List<Task> tasks = taskEntityList.stream().map(t -> t.toTask()).toList();
            return tasks;
        }

        return null;
    }
}
