package com.hitachi.assessment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
		@Email @Size(max = 100) String email,

		@NotNull Boolean active) {

}
