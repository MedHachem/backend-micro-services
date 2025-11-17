package com.example.usermanager.mapper;

import com.example.usermanager.dto.UserDTO;
import com.example.usermanager.entity.Role;
import com.example.usermanager.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);


    default String map(Role role) {
        return role != null ? role.name() : null;
    }

    default Role map(String role) {
        return role != null ? Role.valueOf(role) : null;
    }
}
