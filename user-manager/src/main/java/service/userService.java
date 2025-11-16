package service;

import entity.user;

import java.util.List;

public interface userService {
    user createUser(user user);

    user getUserById(Long id);

    List<user> getAllUsers();

    user updateUser(Long id, user user);

    void deleteUser(Long id);
}
