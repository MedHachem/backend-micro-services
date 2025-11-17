package com.example.usermanager.service;

import com.example.usermanager.dto.UserDTO;
import com.example.usermanager.dto.UserRequestDTO;
import com.example.usermanager.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserDTO getUserById(Long id);
    UserResponseDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();


    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);
}
