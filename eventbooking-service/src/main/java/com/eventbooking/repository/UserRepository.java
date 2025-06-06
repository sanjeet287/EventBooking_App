package com.eventbooking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eventbooking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

}
