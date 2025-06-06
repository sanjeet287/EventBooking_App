package com.eventbooking.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventbooking.dto.UserRequestDTO;
import com.eventbooking.dto.UserResponseDTO;
import com.eventbooking.entity.User;
import com.eventbooking.entity.user_enum.Role;
import com.eventbooking.repository.UserRepository;
import com.eventbooking.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
	
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final ModelMapper modelMapper;

	@Override
	public UserResponseDTO createUser(UserRequestDTO requestDTO) {
		
				User user=User.builder()
						.name(requestDTO.getName())
						.email(requestDTO.getEmail())
						.password(passwordEncoder.encode(requestDTO.getPassword()))
						.role(Role.USER)
						.build();
				
		User savedUser = this.userRepository.save(user);
	        
	        return modelMapper.map(savedUser,UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		 User user = userRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("User not found"));
		return modelMapper.map(user,UserResponseDTO.class);
	}

}
