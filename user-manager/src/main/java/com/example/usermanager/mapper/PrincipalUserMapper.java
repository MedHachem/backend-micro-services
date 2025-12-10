package com.example.usermanager.mapper;

import com.example.usermanager.dto.UserRequestDTO;
import com.example.usermanager.dto.UserResponseDTO;
import com.example.usermanager.entity.Role;
import com.example.usermanager.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PrincipalUserMapper {
    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public User toEntity(UserRequestDTO request) {
        if (request == null) return null;
        User user = new User();
        logger.info("Mapping UserRequestDTO: {}", request);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setFcmToken(request.getFcmToken());
        user.setRole(request.getRole() != null ? Role.valueOf(request.getRole()) : Role.User);
        user.setCreatedAt(LocalDateTime.now());
        logger.info("Mapped User entity: {}", user);
        return user;
    }

    public UserResponseDTO toResponse(User user) {
        if (user == null) return null;
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole() != null ? user.getRole().name() : null)
                .build();
    }

}
