package com.anderson.tasklist.config;

import com.anderson.tasklist.core.task.repository.TaskRepository;
import com.anderson.tasklist.core.task.services.TaskService;
import com.anderson.tasklist.core.task.services.impl.TaskServiceImpl;
import com.anderson.tasklist.core.user.repository.UserRepository;
import com.anderson.tasklist.core.user.services.UserService;
import com.anderson.tasklist.core.user.services.impl.UserServiceImpl;
import com.anderson.tasklist.external.auth.PasswordEncrypt;
import com.anderson.tasklist.external.auth.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserService userService (UserRepository userRepository, PasswordEncrypt passwordEncrypt, TokenService tokenService) {
        return new UserServiceImpl(userRepository, passwordEncrypt, tokenService);
    }

    @Bean
    public TaskService taskService (TaskRepository taskRepository, UserService userService) {
        return new TaskServiceImpl(taskRepository, userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
