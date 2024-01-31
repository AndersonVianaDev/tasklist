package com.anderson.tasklist.adapter.repositories;

import com.anderson.tasklist.adapter.entities.UserEntityAdapter;
import com.anderson.tasklist.core.shared.exceptions.NotFoundException;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public User findByEmail(String email) {
        UserEntityAdapter userEntity = this.repository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email "+ email +" not found !"));

        return userEntity.toUser();
    }
}
