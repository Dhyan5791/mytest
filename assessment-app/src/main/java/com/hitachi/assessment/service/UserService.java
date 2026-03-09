package com.hitachi.assessment.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitachi.assessment.dto.CreateUserRequestDto;
import com.hitachi.assessment.dto.UpdateUserRequestDto;
import com.hitachi.assessment.dto.UserResponseDto;
import com.hitachi.assessment.entity.Role;
import com.hitachi.assessment.entity.User;
import com.hitachi.assessment.mapper.UserMapper;
import com.hitachi.assessment.repository.RoleRepository;
import com.hitachi.assessment.repository.UserRepository;

@Service
public class UserService {
	private final PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public UserResponseDto create(CreateUserRequestDto req) {
		if (userRepository.findByUsername(req.username()).isPresent()) {
		    throw new RuntimeException("USERNAME_EXISTS");
		}

		if (userRepository.findByEmail(req.email()).isPresent()) {
		    throw new RuntimeException("EMAIL_EXISTS");
		}
		
		Set<Role> roles = new HashSet<>(roleRepository.findAllById(req.roleIds()));

        if (roles.size() != req.roleIds().size()) {
            throw new RuntimeException("One or more roleIds not found");
        }

		String hashed = passwordEncoder.encode(req.password());
		User user = new User(req.username(), req.email(), hashed, true);
		
		user.getRoles().addAll(roles);

		User saved = userRepository.save(user);
		return UserMapper.toDto(saved);
	}

	@PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#userId)")
	@Transactional
	public UserResponseDto update(Integer userId, UpdateUserRequestDto req) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		if (req.email() != null) {
			user.setEmail(req.email());
		}

		if (req.active() != null) {
			user.setActive(req.active());
		}

		return UserMapper.toDto(user);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public UserResponseDto getById(Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		return UserMapper.toDto(user);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public List<UserResponseDto> getAll() {
		return userRepository.findAll().stream().map(UserMapper::toDto).toList();
	}
}
