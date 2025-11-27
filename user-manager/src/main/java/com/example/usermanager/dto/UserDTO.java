package com.example.usermanager.dto;

import com.example.usermanager.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
}
