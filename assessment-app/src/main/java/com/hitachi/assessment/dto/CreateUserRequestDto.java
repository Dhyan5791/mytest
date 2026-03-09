package com.hitachi.assessment.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDto(@NotBlank @Size(max = 50) String username,
		@NotBlank @Email @Size(max = 100) String email,
		@NotBlank String password, @NotEmpty Set<Integer> roleIds) {

}
