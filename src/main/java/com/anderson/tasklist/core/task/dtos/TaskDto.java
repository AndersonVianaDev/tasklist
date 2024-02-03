package com.anderson.tasklist.core.task.dtos;

import java.time.LocalDate;

public record TaskDto(String name, Boolean concluded, LocalDate expirationDate) {
}
