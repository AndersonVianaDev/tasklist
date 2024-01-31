package com.anderson.tasklist.core.user.services;

public interface PasswordCryptography {

    String encode(String password);
    boolean toCompare(String password, String rawPassword);
}
