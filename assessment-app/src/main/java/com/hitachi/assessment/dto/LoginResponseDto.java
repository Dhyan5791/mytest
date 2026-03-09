package com.hitachi.assessment.dto;

import java.util.Set;

public record LoginResponseDto(String token,
        Integer userId,
        String username,
        Set<String> roles) {

}
