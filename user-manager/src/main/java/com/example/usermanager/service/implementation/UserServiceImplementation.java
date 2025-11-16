package com.example.usermanager.service.implementation;

import com.example.usermanager.dto.UserDTO;
import com.example.usermanager.entity.User;
import com.example.usermanager.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.usermanager.repository.UserRepository;
import com.example.usermanager.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDTO(user);
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
