package com.eventbooking.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.eventbooking.dto.EventRequestDTO;
import com.eventbooking.dto.EventResponseDTO;
import com.eventbooking.dto.PricingResponseDTO;
import com.eventbooking.entity.Event;
import com.eventbooking.entity.User;
import com.eventbooking.entity.event_enum.Status;
import com.eventbooking.repository.EventRepository;
import com.eventbooking.service.EventService;
import com.eventbooking.service.PricingService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	private final PricingService pricingService;

	private final ModelMapper modelMapper;

	@Override
	public EventResponseDTO createEvent(EventRequestDTO eventRequestDTO, User organizer) {

		PricingResponseDTO pricing = pricingService.getPricingByCategory(eventRequestDTO.getCategory());

		Event event = Event.builder().title(eventRequestDTO.getTitle()).description(eventRequestDTO.getDescription())
				.location(eventRequestDTO.getLocation()).startTime(eventRequestDTO.getStartTime())
				.endTime(eventRequestDTO.getEndTime()).totalSeats(eventRequestDTO.getTotalSeats())
				.availableSeats(eventRequestDTO.getTotalSeats()).organizer(organizer).status(Status.PENDING)
				.createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).category(eventRequestDTO.getCategory())
				.regularSeatPrice(pricing.getRegularSeatPrice()).vipSeatPrice(pricing.getVipSeatPrice()).build();

		Event savedEvent = eventRepository.save(event);

		EventResponseDTO dto = modelMapper.map(savedEvent, EventResponseDTO.class);

		dto.setOrganizerName(organizer.getName());
		dto.setRegularSeatPrice(null);
		dto.setVipSeatPrice(null);
		return dto;
	}
	
	@Override
	public List<EventResponseDTO> getAllEvents(PageRequest pageRequest) {
		List<Event> events = eventRepository.findAll(pageRequest).getContent();
		
		return events.stream().map(event->modelMapper.map(event,EventResponseDTO.class)).collect(Collectors.toList()) ;
	}

	@Override
	public List<EventResponseDTO> getAllEvents(String seatType,PageRequest pageRequest) {
		List<Event> events = eventRepository.findAll(pageRequest).getContent();
		

		 return events.stream().map(event ->{ 
			
			EventResponseDTO dto = modelMapper.map(event, EventResponseDTO.class);
			// Set price based on seat type
	        if ("REGULAR".equalsIgnoreCase(seatType)) {
	           // dto.setPrice(event.getRegularSeatPrice());
	            dto.setRegularSeatPrice(event.getRegularSeatPrice());
	        } else if ("VIP".equalsIgnoreCase(seatType)) {
	            //dto.setPrice(event.getVipSeatPrice());
	            dto.setVipSeatPrice(event.getVipSeatPrice());
	        } else {
	            //dto.setPrice(0.0); 
	        }
	        
	        if (event.getOrganizer() != null) {
	            dto.setOrganizerName(event.getOrganizer().getName());
	        }
	        return dto;
			})
				.collect(Collectors.toList());
	}

	@Override
	public Optional<EventResponseDTO> getEventById(Long id) {

		Optional<Event> event = eventRepository.findById(id);

		return event.map(e -> modelMapper.map(e, EventResponseDTO.class));
	}

	@Override
	public EventResponseDTO updateEvent(Long id, EventRequestDTO updatedEvent) {

		Event existingEvent = eventRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));

		existingEvent.setTitle(updatedEvent.getTitle());
		existingEvent.setDescription(updatedEvent.getDescription());
		existingEvent.setLocation(updatedEvent.getLocation());
		existingEvent.setStartTime(updatedEvent.getStartTime());
		existingEvent.setEndTime(updatedEvent.getEndTime());
		existingEvent.setTotalSeats(updatedEvent.getTotalSeats());
		existingEvent.setAvailableSeats(updatedEvent.getAvailableSeats());
		existingEvent.setStatus(updatedEvent.getStatus());
		existingEvent.setUpdatedAt(updatedEvent.getUpdatedAt());

		return modelMapper.map(existingEvent, EventResponseDTO.class);
	}

	@Override
	public void deleteEvent(Long id) {
		eventRepository.deleteById(id);
	}

	@Override
	public List<EventResponseDTO> getEventsByStatus(String status) {

		List<Event> events = eventRepository.findByStatus(status);
		return events.stream().map(event -> modelMapper.map(event, EventResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<EventResponseDTO> getEventsByOrganizer(Long organizerId) {
		List<Event> OrganiserEvents = eventRepository.findByOrganizerId(organizerId);
		return OrganiserEvents.stream().map(event -> modelMapper.map(event, EventResponseDTO.class))
				.collect(Collectors.toList());
	}

	

}
