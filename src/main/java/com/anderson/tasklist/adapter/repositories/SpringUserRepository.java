package com.anderson.tasklist.adapter.repositories;

import com.anderson.tasklist.adapter.entities.UserEntityAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringUserRepository extends JpaRepository<UserEntityAdapter, UUID> {

    @Query("SELECT u FROM UserEntityAdapter u WHERE u.email = :email")
    Optional<UserEntityAdapter> findUserByEmail(@Param("email") String email);

}
