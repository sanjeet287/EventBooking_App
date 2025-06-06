package com.eventbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventbooking.dto.BookingRequestDTO;
import com.eventbooking.dto.BookingResponseDTO;
import com.eventbooking.entity.User;
import com.eventbooking.security.CustomUserDetails;
import com.eventbooking.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/bookings")
@RestController
@RequiredArgsConstructor
public class BookingController {

	
	private  final BookingService bookingService;

	@PostMapping("/newBooking")
	public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO,
			 @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		 User user = userDetails.getUser();

		BookingResponseDTO createdBooking = bookingService.createBooking(bookingRequestDTO, user.getId());
		return ResponseEntity.ok(createdBooking);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<BookingResponseDTO>> getBookingsByUser(@PathVariable Long userId) {
		List<BookingResponseDTO> bookings = bookingService.getBookingsByUser(userId);
		return ResponseEntity.ok(bookings);
	}

	@GetMapping("/event/{eventId}/user/{userId}")
	public ResponseEntity<List<BookingResponseDTO>> getBookingByEventAndUser(@PathVariable Long eventId,
			@PathVariable Long userId) {

		 List<BookingResponseDTO> bookings = bookingService.getBookingByEventAndUser(eventId, userId);
		    
		    if (bookings == null || bookings.isEmpty()) {
		        return ResponseEntity.notFound().build();
		    }
		    
		    return ResponseEntity.ok(bookings);
	}

	@DeleteMapping("/{bookingId}")
	public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId, @RequestParam Long userId) {
		bookingService.cancelBooking(bookingId, userId);
		return ResponseEntity.noContent().build();
	}

}
