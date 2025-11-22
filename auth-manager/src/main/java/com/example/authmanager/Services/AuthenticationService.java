package com.example.authmanager.Services;


import com.example.authmanager.DAO.Request.SignInRequest;
import com.example.authmanager.DAO.Request.SignUpRequest;
import com.example.authmanager.DAO.Response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}
