package com.example.authmanager.Controller;


import com.example.authmanager.DAO.Request.SignInRequest;
import com.example.authmanager.DAO.Response.JwtAuthenticationResponse;
import com.example.authmanager.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Here is your resource hech");
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
