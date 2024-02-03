package com.anderson.tasklist.adapter.repositories;

import com.anderson.tasklist.adapter.entities.UserEntityAdapter;
import com.anderson.tasklist.core.shared.exceptions.NotFoundException;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final SpringUserRepository repository;

    public UserRepositoryAdapter(SpringUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(User user) {
        UserEntityAdapter userEntityAdapter = new UserEntityAdapter(user);

        this.repository.save(userEntityAdapter);
    }

    @Override
    public User findById(UUID id) {
        Optional<UserEntityAdapter> userEntityAdapter = this.repository.findById(id);

        if(userEntityAdapter.isPresent()) return userEntityAdapter.get().toUser();

        return null;
    }

    @Override
    public User findByEmail(String email) {
        Optional<UserEntityAdapter> userEntity = this.repository.findUserByEmail(email);

        if(userEntity.isPresent()) return userEntity.get().toUser();

        return null;
    }

    @Override
    public User save(User user) {
        String email = user.getEmail();

        UserEntityAdapter userEntityAdapter = this.repository.findUserByEmail(user.getEmail()).orElseThrow(() -> new NotFoundException("User with email "+ email +" not found !"));

        userEntityAdapter.setPassword(user.getPassword());

        return this.repository.save(userEntityAdapter).toUser();
    }

    @Override
    public void delete(User user) {
        UUID id = user.getId();

        UserEntityAdapter userEntityAdapter = this.repository.findById(id).orElseThrow(() -> new NotFoundException("User with id "+ id +" not found !"));

        this.repository.delete(userEntityAdapter);
    }
}
