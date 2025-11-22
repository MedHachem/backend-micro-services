package com.example.authmanager.Services.Implem;

import com.example.authmanager.DAO.Request.SignInRequest;
import com.example.authmanager.DAO.Request.SignUpRequest;
import com.example.authmanager.DAO.Response.JwtAuthenticationResponse;
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

        // 1️⃣ Encoder le mot de passe
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 2️⃣ Construire UserRequest pour envoyer au UserManager MS
        UserRequest userRequest = new UserRequest(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                encodedPassword,
                request.getRole()
        );

        // 3️⃣ Créer l'utilisateur via Feign Client
        UserResponse createdUser = userManagerClient.createUser(userRequest);

        // 4️⃣ Logger pour debug
        logger.info("User created in UserManager: id={}, firstname={}, lastname={}, email={}, role={}",
                createdUser.id(),
                createdUser.firstname(),
                createdUser.lastname(),
                createdUser.email(),
                createdUser.role());

        // 5️⃣ Construire UserPrincipal pour Spring Security (avec mot de passe encodé)
        UserPrincipal userPrincipal = new UserPrincipal(
                createdUser.email(),
                encodedPassword,          // ici mot de passe encodé
                createdUser.role()
        );

        // 6️⃣ Claims pour JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", createdUser.role());

        // 7️⃣ Générer le token JWT
        String jwt = jwtService.generateToken(userPrincipal);

        // 8️⃣ Retourner la réponse
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .id(createdUser.id())
                .firstName(createdUser.firstname())
                .lastName(createdUser.lastname())
                .role(createdUser.role())
                .build();
    }



    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {

// --- Log UserPrincipal ---
        logger.info("UserPrincipal created: email={}, password={}",
                request.getEmail(),
                request.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException ex) {
            logger.error("Authentication failed for {}: {}", request.getEmail(), ex.getMessage());
            throw ex; // ou gérer selon ton besoin
        }
        var user = userManagerClient.getByEmail(request.getEmail());
        logger.info("UserPrincipal by email: {}", user);

        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        UserDetails userPrincipal = new UserPrincipal(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.role());
        String jwt = jwtService.generateToken(userPrincipal);
        return JwtAuthenticationResponse.builder().token(jwt)
                .id(user.id())
                .firstName(user.firstname())
                .lastName(user.lastname())
                .role(user.role())
                .build();
    }


}
