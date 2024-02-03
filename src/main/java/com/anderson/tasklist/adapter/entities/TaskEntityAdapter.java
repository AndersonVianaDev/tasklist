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
    private UUID idUser;
    private String name;
    private Boolean concluded;
    private LocalDate expirationDate;

    public TaskEntityAdapter() {
    }

    public TaskEntityAdapter(Task task) {
        this.idUser = task.getIdUser();
        this.name = task.getName();
        this.concluded = task.getConcluded();
        this.expirationDate = task.getExpirationDate();
    }

    public Task toTask() {
        return new Task(id, idUser, name, concluded, expirationDate);
    }
}
