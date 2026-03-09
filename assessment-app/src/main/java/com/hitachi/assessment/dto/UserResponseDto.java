package com.hitachi.assessment.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDto(
		Integer userId,
        String username,
        String email,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Set<String> roles) {

}
