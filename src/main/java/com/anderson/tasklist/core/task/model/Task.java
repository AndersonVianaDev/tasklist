package com.anderson.tasklist.core.task.model;

import com.anderson.tasklist.core.task.dtos.TaskDto;
import com.anderson.tasklist.core.user.model.User;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Task {
    private UUID id;
    private User user;
    private String name;
    private Boolean concluded;
    private LocalDate expirationDate;

    public Task(UUID id, User user, String name, Boolean concluded, LocalDate expirationDate) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.concluded = concluded;
        this.expirationDate = expirationDate;
    }

    public Task(TaskDto taskDto) {
        this.name = taskDto.name();
        this.concluded = taskDto.concluded();
        this.expirationDate = taskDto.expirationDate();
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getConcluded() {
        return concluded;
    }

    public void setConcluded(Boolean concluded) {
        this.concluded = concluded;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
