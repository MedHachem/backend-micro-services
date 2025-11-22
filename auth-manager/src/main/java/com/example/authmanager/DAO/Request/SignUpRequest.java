package com.example.authmanager.DAO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SignUpRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role ;
}
