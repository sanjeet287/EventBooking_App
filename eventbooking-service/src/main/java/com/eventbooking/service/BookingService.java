package com.eventbooking.service;

import java.util.List;

import com.eventbooking.dto.BookingRequestDTO;
import com.eventbooking.dto.BookingResponseDTO;

public interface BookingService {

	BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, Long userId);

	List<BookingResponseDTO> getBookingsByUser(Long userId);

	List<BookingResponseDTO> getBookingByEventAndUser(Long eventId, Long userId);

	void cancelBooking(Long bookingId, Long userId);

}
