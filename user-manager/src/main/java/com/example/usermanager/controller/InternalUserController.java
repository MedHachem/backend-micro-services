package com.example.usermanager.controller;

import com.example.usermanager.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
public class InternalUserController {

    private final UserRepository userRepository;

    public InternalUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Endpoint interne pour récupérer le mot de passe encodé
    @GetMapping("/{email}/encoded-password")
    public ResponseEntity<String> getEncodedPassword(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .map(user -> ResponseEntity.ok(user.getPassword()))
                .orElse(ResponseEntity.notFound().build());
    }
}

