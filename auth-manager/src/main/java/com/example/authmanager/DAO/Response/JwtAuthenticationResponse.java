package com.example.authmanager.DAO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String lastName;
    private String firstName;
    private String token ;
    private Long  id;
    private String role;
}
