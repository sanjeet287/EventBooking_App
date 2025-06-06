package com.eventbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import com.eventbooking.dto.EventRequestDTO;
import com.eventbooking.dto.EventResponseDTO;
import com.eventbooking.entity.User;

public interface EventService {

	EventResponseDTO createEvent(EventRequestDTO eventRequestdto, User organiser);

	List<EventResponseDTO> getAllEvents(String seatType,PageRequest pageRequest);
	
	List<EventResponseDTO> getAllEvents(PageRequest pageRequest);

	Optional<EventResponseDTO> getEventById(Long id);

	EventResponseDTO updateEvent(Long id, EventRequestDTO eventRequestdto);

	void deleteEvent(Long id);

	List<EventResponseDTO> getEventsByStatus(String status);

	List<EventResponseDTO> getEventsByOrganizer(Long organizerId);

}
