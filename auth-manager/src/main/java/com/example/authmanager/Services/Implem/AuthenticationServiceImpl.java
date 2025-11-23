package com.example.authmanager.Services.Implem;

import com.example.authmanager.DAO.Request.SignInRequest;
import com.example.authmanager.DAO.Request.SignUpRequest;
import com.example.authmanager.DAO.Response.JwtAuthenticationResponse;
import com.example.authmanager.DAO.Response.JwtAuthorizationResponse;
import com.example.authmanager.Services.AuthenticationService;
import com.example.authmanager.Services.JwtService;
import com.example.authmanager.User.UserPrincipal;
import com.example.authmanager.client.UserManagerClient;
import com.example.authmanager.dto.UserRequest;
import com.example.authmanager.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserManagerClient userManagerClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserRequest userRequest = new UserRequest(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                encodedPassword,
                request.getRole()
        );
        UserResponse createdUser = userManagerClient.createUser(userRequest);
        return JwtAuthenticationResponse.builder()
                .id(createdUser.id())
                .firstName(createdUser.firstname())
                .lastName(createdUser.lastname())
                .role(createdUser.role())
                .build();
    }



    @Override
    public JwtAuthorizationResponse signin(SignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException ex) {
            logger.error("Authentication failed for {}: {}", request.getEmail(), ex.getMessage());
            throw ex;
        }
        var user = userManagerClient.getByEmail(request.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        UserDetails userPrincipal = new UserPrincipal(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.role());
        String jwt = jwtService.generateToken(userPrincipal);
        return JwtAuthorizationResponse.builder().token(jwt)
                .id(user.id())
                .role(user.role())
                .build();
    }


}
