package com.spring.usersecurity.dto;

public record UserRequest(
        String firstname,
        String lastname,
        String email,
        String password,
        String role
) {}