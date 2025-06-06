package com.eventbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventbooking.entity.Event;

public interface EventRepository extends JpaRepository<Event,Long> {
	
	List<Event> findByStatus(String status);

    List<Event> findByOrganizerId(Long organizerId);

}
