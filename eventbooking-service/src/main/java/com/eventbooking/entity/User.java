package com.eventbooking.entity;

import java.time.LocalDateTime;

import com.eventbooking.entity.user_enum.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(unique = true)
	private String email;

	private String password;

	private String phoneNo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;        // USER, ORGANIZER, ADMIN

	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

}
