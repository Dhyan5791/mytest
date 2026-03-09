package com.hitachi.assessment.security;

import java.util.Set;

public record AuthUserPrincipal(Integer userId,
        String username,
        Set<String> roles) {

}
