package com.eventbooking.dto;

import com.eventbooking.entity.user_enum.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {

	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;

	@Size(min = 6, message = "Password should have at least 6 characters")
	private String password;
	
	private Role role; 
	
	@NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain only digits")
    private String phoneNo;
}
