package com.example.authmanager.Services;


import com.example.authmanager.DAO.Request.SignInRequest;
import com.example.authmanager.DAO.Request.SignUpRequest;
import com.example.authmanager.DAO.Response.JwtAuthenticationResponse;
import com.example.authmanager.DAO.Response.JwtAuthorizationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthorizationResponse signin(SignInRequest request);
}
