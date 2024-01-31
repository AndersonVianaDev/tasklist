package com.anderson.tasklist.external.auth;

import com.anderson.tasklist.core.user.services.PasswordCryptography;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncrypt implements PasswordCryptography {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncrypt(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean toCompare(String password, String rawPassword) {
        return passwordEncoder.matches(rawPassword, password);
    }
}

