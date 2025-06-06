package com.eventbooking.dto;

import com.eventbooking.entity.user_enum.Role;

import lombok.Data;

@Data
public class UserResponseDTO {

	private Long id;
	private String name;
	private String email;
	private Role role;

}
