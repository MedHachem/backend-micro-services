package com.spring.usersecurity.dto;

public record UserResponse(
        Long id,
        String firstname,
        String lastname,
        String email,
        String role,
        String password,
        Boolean enabled
) {}