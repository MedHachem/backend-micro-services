package com.example.authmanager.dto;

public record UserRequest(
        String firstname,
        String lastname,
        String email,
        String password,
        String role
) {}