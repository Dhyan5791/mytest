package com.hitachi.assessment.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.assessment.dto.CreateUserRequestDto;
import com.hitachi.assessment.dto.UpdateUserRequestDto;
import com.hitachi.assessment.dto.UserResponseDto;
import com.hitachi.assessment.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponseDto create(@Valid @RequestBody CreateUserRequestDto req) {
        return userService.create(req);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Integer id,
                                  @Valid @RequestBody UpdateUserRequestDto req) {
        return userService.update(id, req);
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }
}
