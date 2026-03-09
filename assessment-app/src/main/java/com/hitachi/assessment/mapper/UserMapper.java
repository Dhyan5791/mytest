package com.hitachi.assessment.mapper;

import java.util.stream.Collectors;

import com.hitachi.assessment.dto.UserResponseDto;
import com.hitachi.assessment.entity.Role;
import com.hitachi.assessment.entity.User;

public class UserMapper {
	public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getRoles()
                        .stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toSet())
        );
    }
}
