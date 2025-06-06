package com.eventbooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventbooking.dto.EventRequestDTO;
import com.eventbooking.dto.EventResponseDTO;
import com.eventbooking.entity.User;
import com.eventbooking.security.CustomUserDetails;
import com.eventbooking.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
	
	
	private final EventService eventService;

	@PostMapping("/newEvent")
	public ResponseEntity<EventResponseDTO> createEvent(@Valid @RequestBody EventRequestDTO eventRequestDTO,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		
		 User organizer = userDetails.getUser();
		 
		// System.out.print("User fetched from DB: "+organizer.getEmail()+" "+organizer.getName()+" "+organizer.getRole()+" "+organizer.getPhoneNo());


		EventResponseDTO createdEvent = eventService.createEvent(eventRequestDTO, organizer);

		return ResponseEntity.ok(createdEvent);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<EventResponseDTO> createEvent(@Valid @RequestBody EventRequestDTO eventRequestDTO,
			@PathVariable Long id) {

		EventResponseDTO updatedEvent = eventService.updateEvent(id, eventRequestDTO);

		return ResponseEntity.ok(updatedEvent);
	}

	@DeleteMapping("/cancel/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
		eventService.deleteEvent(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{eventId}")
	public ResponseEntity<EventResponseDTO> getEvent(@PathVariable Long eventId) {
	    Optional<EventResponseDTO> event = eventService.getEventById(eventId);
	    return event.map(ResponseEntity::ok)
	                 .orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/")
	public ResponseEntity<List<EventResponseDTO>> getAllEvents(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		List<EventResponseDTO> allEvents = eventService.getAllEvents(PageRequest.of(page, size));

		return ResponseEntity.ok(allEvents);
	}
	@GetMapping("/all")
	public ResponseEntity<List<EventResponseDTO>> getAllEvents(@RequestBody String seatType ,@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		List<EventResponseDTO> allEvents = eventService.getAllEvents(seatType,PageRequest.of(page, size));

		return ResponseEntity.ok(allEvents);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<EventResponseDTO>> getEventsByStatus(@PathVariable String status) {
		List<EventResponseDTO> events = eventService.getEventsByStatus(status);
		return ResponseEntity.ok(events);
	}

	@GetMapping("/organizer/{organizerId}")
	public ResponseEntity<List<EventResponseDTO>> getEventsByOrganizer(@PathVariable Long organizerId) {
		List<EventResponseDTO> events = eventService.getEventsByOrganizer(organizerId);
		return ResponseEntity.ok(events);
	}

}
