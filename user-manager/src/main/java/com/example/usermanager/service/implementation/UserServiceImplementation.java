package com.example.usermanager.service.implementation;

import com.example.usermanager.dto.*;
import com.example.usermanager.entity.Role;
import com.example.usermanager.entity.User;
import com.example.usermanager.mapper.PrincipalUserMapper;
import com.example.usermanager.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.example.usermanager.repository.UserRepository;
import com.example.usermanager.service.UserService;

import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserMapper userMapper;
    private final PrincipalUserMapper principalUserMapper;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);
    private final RabbitTemplate rabbitTemplate;
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        logger.info("Received UserRequestDTO: {}", userRequestDTO);

        User user = principalUserMapper.toEntity(userRequestDTO);
        user.setCreatedAt(LocalDateTime.now());

        if (user.getRole() == null) {user.setRole(Role.User);}

        User savedUser = userRepository.save(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", user.getFirstname() + " " + user.getLastname());
        NotificationRequestDTO emailReq = NotificationRequestDTO.builder()
                .eventType("user_registration")
                .contentType("email")
                .email(user.getEmail())
                .phone(user.getPhone())
                .fcmToken(user.getFcmToken())
                .data(variables)
                .build();
        NotificationRequestDTO smsReq = NotificationRequestDTO.builder()
                .eventType("user_registration")
                .contentType("sms")
                .email(user.getEmail())
                .phone(user.getPhone())
                .fcmToken(user.getFcmToken())
                .data(variables)
                .build();
        rabbitTemplate.convertAndSend(
                "userExchange",
                "user.registration.created",
                smsReq
        );
        rabbitTemplate.convertAndSend(
                "userExchange",
                "user.registration.created",
                emailReq
        );

        return principalUserMapper.toResponse(savedUser);
    }



    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));

        return principalUserMapper.toResponse(user);
    }
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setFirstname(userDTO.getFirstname());
        existing.setLastname(userDTO.getLastname());
        existing.setEmail(userDTO.getEmail());
        existing.setPhone(userDTO.getPhone());
        existing.setFcmToken(userDTO.getFcmToken());
        User updated = userRepository.save(existing);
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", updated.getFirstname() + " " + updated.getLastname());
        variables.put("email",updated.getEmail());

        NotificationRequestDTO smsReq = NotificationRequestDTO.builder()
                .eventType("reset_password")
                .contentType("sms")
                .phone(updated.getPhone())
                .data(variables)
                .build();
        rabbitTemplate.convertAndSend(
                "userExchange",
                "user.profile.updated",
                smsReq
        );
        return userMapper.toDTO(updated);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
