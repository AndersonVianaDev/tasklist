package com.anderson.tasklist.adapter.entities;

import com.anderson.tasklist.core.task.model.Task;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_tasks")
public class TaskEntityAdapter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntityAdapter user;
    private String name;
    private Boolean concluded;
    private LocalDate expirationDate;

    public TaskEntityAdapter() {
    }

    public TaskEntityAdapter(Task task) {
        this.user = new UserEntityAdapter(task.getUser());
        this.name = task.getName();
        this.concluded = task.getConcluded();
        this.expirationDate = task.getExpirationDate();
    }

    public Task toTask() {
        return new Task(id, user.toUser(), name, concluded, expirationDate);
    }

    public void setUser(UserEntityAdapter user) {
        this.user = user;
    }

    public void setConcluded(Boolean concluded) {
        this.concluded = concluded;
    }
}
