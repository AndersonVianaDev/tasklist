package com.anderson.tasklist.adapter.repositories.task;

import com.anderson.tasklist.adapter.entities.TaskEntityAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringTaskRepository extends JpaRepository<TaskEntityAdapter, UUID> {
}
