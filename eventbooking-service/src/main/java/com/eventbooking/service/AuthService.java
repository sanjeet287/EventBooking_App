package com.eventbooking.service;

import com.eventbooking.dto.AuthResponseDTO;
import com.eventbooking.dto.LoginRequestDTO;
import com.eventbooking.dto.SignupRequestDTO;
import com.eventbooking.dto.SignupResponseDTO;

public interface AuthService {
	
	SignupResponseDTO signup(SignupRequestDTO signupRequestDTO);
    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);

}
