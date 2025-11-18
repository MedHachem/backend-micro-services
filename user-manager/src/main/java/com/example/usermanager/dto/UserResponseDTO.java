package com.example.usermanager.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
}
