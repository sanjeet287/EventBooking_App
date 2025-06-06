package com.eventbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDTO {
	
	
	private String email;
	private String password;
	private String name;	
	private String phoneNo;
	private String message;

}
