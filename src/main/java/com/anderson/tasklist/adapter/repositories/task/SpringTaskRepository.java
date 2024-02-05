package com.anderson.tasklist.adapter.repositories.task;

import com.anderson.tasklist.adapter.entities.TaskEntityAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringTaskRepository extends JpaRepository<TaskEntityAdapter, UUID> {

    @Query("SELECT t FROM TaskEntityAdapter t where t.user.id = :idUser")
    List<TaskEntityAdapter> findAll(@Param("idUser") UUID idUser);

    @Query("SELECT t FROM TaskEntityAdapter t WHERE t.user.id = :idUser AND t.concluded = false")
    List<TaskEntityAdapter> findAllActive(@Param("idUser") UUID idUser);

}
