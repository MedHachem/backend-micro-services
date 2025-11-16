package service.implementation;

import entity.user;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.userRepository;
import service.userService;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class userServiceImplementation implements userService {
    private final userRepository userRepository;
    @Override
    public user createUser(user user) {
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public user getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<user> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public user updateUser(Long id, user user) {
        user existing = getUserById(id);
        existing.setFirstname(user.getFirstname());
        existing.setLastname(user.getLastname());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
