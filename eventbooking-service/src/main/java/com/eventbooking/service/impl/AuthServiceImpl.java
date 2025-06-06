package com.eventbooking.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventbooking.dto.AuthResponseDTO;
import com.eventbooking.dto.LoginRequestDTO;
import com.eventbooking.dto.SignupRequestDTO;
import com.eventbooking.dto.SignupResponseDTO;
import com.eventbooking.entity.User;
import com.eventbooking.entity.user_enum.Role;
import com.eventbooking.repository.UserRepository;
import com.eventbooking.security.CustomUserDetails;
import com.eventbooking.security.JwtTokenProvider;
import com.eventbooking.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	private final JwtTokenProvider jwtTokenProvider;

	private final AuthenticationManager authenticationManager;

	private final PasswordEncoder passwordEncoder;

	private final ModelMapper modelMapper;

	@Override
	public SignupResponseDTO signup(SignupRequestDTO signupRequestDTO) {

		User user = new User();
		user.setEmail(signupRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
		user.setName(signupRequestDTO.getFirstName() + " " + signupRequestDTO.getLastName());
		user.setPhoneNo(signupRequestDTO.getPhoneNo());
		user.setRole(Role.USER); // default role

		User savedUser = userRepository.save(user);
		
		

		SignupResponseDTO responseDTO = modelMapper.map(savedUser, SignupResponseDTO.class);
		responseDTO.setMessage("User registered successfully");
		return responseDTO;

	}

	@Override
	public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		String token = jwtTokenProvider.generateToken(userDetails);

		return new AuthResponseDTO(token, "User logged in successfully");
	}

}
