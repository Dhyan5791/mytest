package com.hitachi.assessment.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitachi.assessment.dto.LoginRequestDto;
import com.hitachi.assessment.dto.LoginResponseDto;
import com.hitachi.assessment.entity.Role;
import com.hitachi.assessment.entity.User;
import com.hitachi.assessment.repository.UserRepository;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Transactional(readOnly = true)
	public LoginResponseDto login(LoginRequestDto req) {

		User user = userRepository.findByUsernameOrEmail(req.login(), req.login())
				.orElseThrow(() -> new RuntimeException("Invalid username/email or password"));

		if (!user.isActive()) {
			throw new RuntimeException("User is inactive");
		}

		if (!passwordEncoder.matches(req.password(), user.getPassword())) {
			throw new RuntimeException("Invalid username/email or password");
		}

		Set<String> roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());

		String token = jwtService.generateToken(user.getUserId(), user.getUsername(), roles);

		return new LoginResponseDto(token, user.getUserId(), user.getUsername(), roles);
	}

}
