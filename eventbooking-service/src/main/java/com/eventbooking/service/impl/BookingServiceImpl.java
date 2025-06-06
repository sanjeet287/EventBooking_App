package com.eventbooking.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.eventbooking.booking.rabbitmq.publisher.PaymentRequestPublisher;
import com.eventbooking.dto.BookingRequestDTO;
import com.eventbooking.dto.BookingResponseDTO;
import com.eventbooking.dto.PaymentInitiationEvent;
import com.eventbooking.dto.PricingRequestDTO;
import com.eventbooking.dto.PricingResponseDTO;
import com.eventbooking.entity.Booking;
import com.eventbooking.entity.Event;
import com.eventbooking.entity.Pricing;
import com.eventbooking.entity.User;
import com.eventbooking.entity.event_enum.BookingPaymentStatus;
import com.eventbooking.entity.event_enum.BookingStatus;
import com.eventbooking.repository.BookingRepository;
import com.eventbooking.repository.EventRepository;
import com.eventbooking.repository.PricingRepository;
import com.eventbooking.repository.UserRepository;
import com.eventbooking.service.BookingService;
import com.eventbooking.service.PricingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;

	private final EventRepository eventRepository;

	private final UserRepository userRepository;

	private final PricingRepository pricingRepository;

	private final PricingService pricingService;

	private final ModelMapper modelMapper;

	private final PaymentRequestPublisher paymentRequestPublisher;

	@Override
	public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, Long userId) {

		Event event = eventRepository.findById(bookingRequestDTO.getEventId())
				.orElseThrow(() -> new RuntimeException("Event not found"));

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		if (event.getAvailableSeats() < bookingRequestDTO.getNumberOfSeats()) {
			throw new RuntimeException("Not enough available seats");
		}

		// Fetch base pricing for event category
		Pricing basePricing = pricingRepository.findByCategory(event.getCategory())
				.orElseThrow(() -> new RuntimeException("Pricing not found for event category"));

		// Convert to PricingRequestDTO
		PricingRequestDTO basePricingDTO = PricingRequestDTO.builder().category(event.getCategory())
				.regularSeatPrice(basePricing.getRegularSeatPrice()).vipSeatPrice(basePricing.getVipSeatPrice())
				.build();

		// Apply surge pricing
		int totalSeats = event.getTotalSeats();
		int bookedSeats = totalSeats - event.getAvailableSeats();

		PricingResponseDTO surgePricing = pricingService.applySurgePricing(basePricingDTO, totalSeats, bookedSeats);

		// Determine seat type (REGULAR or VIP) — assuming REGULAR for now
		Double totalAmount = 0.0;
		if (bookingRequestDTO.getSeatType().equals("REGULAR")) {
			Double pricePerSeat = surgePricing.getRegularSeatPrice();
			totalAmount = pricePerSeat * bookingRequestDTO.getNumberOfSeats();
		} else {
			Double pricePerSeat = surgePricing.getVipSeatPrice();
			totalAmount = pricePerSeat * bookingRequestDTO.getNumberOfSeats();
		}

		// Create booking
		Booking booking = Booking.builder().event(event).user(user).numberOfSeats(bookingRequestDTO.getNumberOfSeats())
				.totalAmount(totalAmount).bookingPaymentstatus(BookingPaymentStatus.PENDING)
				.bookingStatus(BookingStatus.PENDING).seatType(bookingRequestDTO.getSeatType())
				.bookingTime(LocalDateTime.now()).build();

		// Update available seats
		event.setAvailableSeats(event.getAvailableSeats() - bookingRequestDTO.getNumberOfSeats());

		// saving booking & event to DB

		eventRepository.save(event);

		Booking savedBooking = bookingRepository.save(booking);

		log.info("Iniating the payment for booking ");

		// publish the payment event to rabbitmq for payment intiate

		PaymentInitiationEvent paymentEvent = new PaymentInitiationEvent(userId, event.getId(), totalAmount,
				bookingRequestDTO.getNumberOfSeats());

		log.info("Iniating the payment for booking of event Details: ", paymentEvent.toString());

		paymentRequestPublisher.publishPaymentRequest(paymentEvent);

		return modelMapper.map(savedBooking, BookingResponseDTO.class);
	}

	@Override
	public List<BookingResponseDTO> getBookingsByUser(Long userId) {
		List<Booking> bookings = bookingRepository.findByUserId(userId);
		return bookings.stream().map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public void cancelBooking(Long bookingId, Long userId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));

		if (!booking.getUser().getId().equals(userId)) {
			throw new RuntimeException("You can only cancel your own bookings");
		}

		booking.setBookingStatus(BookingStatus.CANCELLED);

		Event event = booking.getEvent();
		event.setAvailableSeats(event.getAvailableSeats() + booking.getNumberOfSeats());
		eventRepository.save(event);

		bookingRepository.save(booking);

	}

	@Override
	public List<BookingResponseDTO> getBookingByEventAndUser(Long eventId, Long userId) {
		List<Booking> bookings = bookingRepository.findByEventIdAndUserId(eventId, userId);

		return bookings.stream().map(book -> modelMapper.map(book, BookingResponseDTO.class))
				.collect(Collectors.toList());
	}

}
