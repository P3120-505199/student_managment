private UserResponseDto toDto(User user) {
    UserResponseDto dto = new UserResponseDto();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setEmail(user.getEmail());
    dto.setRole(user.getRole().name());
    return dto;
}