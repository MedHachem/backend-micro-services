package com.example.usermanager.service.implementation;

import com.example.usermanager.dto.UserDTO;
import com.example.usermanager.dto.UserRequestDTO;
import com.example.usermanager.dto.UserResponseDTO;
import com.example.usermanager.entity.Role;
import com.example.usermanager.entity.User;
import com.example.usermanager.mapper.PrincipalUserMapper;
import com.example.usermanager.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.usermanager.repository.UserRepository;
import com.example.usermanager.service.UserService;

import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserMapper userMapper;
    private final PrincipalUserMapper principalUserMapper;
    private final UserRepository userRepository;
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = principalUserMapper.toEntity(userRequestDTO);
        user.setCreatedAt(LocalDateTime.now());

        if (user.getRole() == null) {
            user.setRole(Role.User);
        }
        return principalUserMapper.toResponse(userRepository.save(user));
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
        User updated = userRepository.save(existing);
        return userMapper.toDTO(updated);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
