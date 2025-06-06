package com.eventbooking.controller;

import com.eventbooking.dto.AuthResponseDTO;
import com.eventbooking.dto.LoginRequestDTO;
import com.eventbooking.dto.SignupRequestDTO;
import com.eventbooking.dto.SignupResponseDTO;
import com.eventbooking.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<SignupResponseDTO> register(@Valid @RequestBody SignupRequestDTO registerDto) {
		System.out.println("Url is being hit...");
		SignupResponseDTO response = authService.signup(registerDto);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDto) {
        AuthResponseDTO response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
}
