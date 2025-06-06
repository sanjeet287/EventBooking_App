package com.eventbooking.service;


import com.eventbooking.dto.UserRequestDTO;
import com.eventbooking.dto.UserResponseDTO;


public interface UserService {

	UserResponseDTO createUser(UserRequestDTO userRequestDTO);

	UserResponseDTO getUserById(Long id);

}
