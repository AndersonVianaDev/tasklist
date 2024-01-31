package com.anderson.tasklist.adapter.repositories;

import com.anderson.tasklist.adapter.entities.UserEntityAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface AuthorizationRepository extends JpaRepository<UserEntityAdapter, UUID> {
    UserDetails findByEmail(String email);
}
