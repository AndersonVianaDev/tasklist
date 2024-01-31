package com.anderson.tasklist.config;

import com.anderson.tasklist.core.user.repository.UserRepository;
import com.anderson.tasklist.core.user.services.UserService;
import com.anderson.tasklist.core.user.services.impl.UserServiceImpl;
import com.anderson.tasklist.external.auth.PasswordEncrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserService userService (UserRepository userRepository, PasswordEncrypt passwordEncrypt) {
        return new UserServiceImpl(userRepository, passwordEncrypt);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
