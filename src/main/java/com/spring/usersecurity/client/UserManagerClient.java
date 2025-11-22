package com.spring.usersecurity.client;

import com.spring.usersecurity.dto.UserRequest;
import com.spring.usersecurity.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-manager", url = "${usermanager.url}")
public interface UserManagerClient {
    @PostMapping("/api/users")
    UserResponse createUser(@RequestBody UserRequest request);

    @GetMapping("/api/users/{id}")
    UserResponse getById(@PathVariable Long id);

    @GetMapping("/api/users/userByEmail/{email}")
    UserResponse getByEmail(@PathVariable String email);

    @GetMapping("/api/users")
    List<UserResponse> getAllUsers();

    @GetMapping("/internal/users/{email}/encoded-password")
    String getEncodedPassword(@PathVariable String email);
}
