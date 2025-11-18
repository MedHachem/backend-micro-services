package com.example.usermanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
}

