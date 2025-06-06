package com.eventbooking.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventbooking.dto.UserRequestDTO;
import com.eventbooking.dto.UserResponseDTO;
import com.eventbooking.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	 private final UserService userService;

	    @PostMapping
	    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
	        return userService.createUser(userRequestDTO);
	    }

	    @GetMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public UserResponseDTO getUser(@PathVariable Long id) {
	        return userService.getUserById(id);
	    }

}
