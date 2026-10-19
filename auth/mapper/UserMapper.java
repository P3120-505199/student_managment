package com.example.studentapp.auth.mapper;

import com.example.studentapp.auth.dto.UserResponseDto;
import com.example.studentapp.auth.entity.User;

public final class UserMapper {
    private UserMapper() {}

    public static UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        return dto;
    }
}