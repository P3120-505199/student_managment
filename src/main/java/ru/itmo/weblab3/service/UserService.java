package ru.itmo.weblab3.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ru.itmo.weblab3.dto.auth.RegisterRequestDto;
import ru.itmo.weblab3.dto.auth.UserResponseDto;
import ru.itmo.weblab3.entity.Role;
import ru.itmo.weblab3.entity.User;
import ru.itmo.weblab3.exception.ConflictException;
import ru.itmo.weblab3.repository.UserRepository;

import java.util.Locale;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordService passwordService;

    @Transactional
    public UserResponseDto register(RegisterRequestDto req) {
        String username = req.getUsername().trim();
        String email = req.getEmail().trim().toLowerCase(Locale.ROOT);

        if (userRepository.existsByUsername(username)) {
            throw new ConflictException("Username already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Email already registered");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordService.hash(req.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        return toDto(user);
    }

    @Transactional
    public User createAdminIfNotExists(String username, String email, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            return userRepository.findByUsername(username).orElse(null);
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email.toLowerCase(Locale.ROOT));
        user.setPasswordHash(passwordService.hash(rawPassword));
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    private UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        return dto;
    }
}