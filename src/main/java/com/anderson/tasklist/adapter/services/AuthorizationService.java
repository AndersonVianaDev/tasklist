package com.anderson.tasklist.adapter.services;

import com.anderson.tasklist.adapter.entities.UserEntityAdapter;
import com.anderson.tasklist.adapter.repositories.user.SpringUserRepository;
import com.anderson.tasklist.core.shared.exceptions.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationService implements UserDetailsService {

    private final SpringUserRepository repository;

    public AuthorizationService(SpringUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UUID idUser = UUID.fromString(id);
        UserEntityAdapter user = this.repository.findById(idUser).orElseThrow(() ->  new NotFoundException("User with id"+ id +" not found"));

        return user;
    }
}
