package com.eventbooking.booking.rabbitmq.listener;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventbooking.config.RabbitMQConfigConstant;
//import com.eventbooking.config.RabbitMQConfigConstant;
import com.eventbooking.dto.PaymentStatusEvent;
import com.eventbooking.entity.Booking;
import com.eventbooking.entity.event_enum.BookingPaymentStatus;
import com.eventbooking.entity.event_enum.BookingStatus;
import com.eventbooking.repository.BookingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentStatusConsumer {

	private final BookingRepository bookingRepository;

	
	@RabbitListener(queues = RabbitMQConfigConstant.QUEUE)
	public void handlePaymentStatus(PaymentStatusEvent event) {
		log.info("Received payment status update: {}", event);

		List<Booking> bookings = bookingRepository.findByEventIdAndUserId(
				event.getEventId(), event.getUserId());

		if (bookings.isEmpty()) {
			log.warn("No bookings found for userId={}, eventId={}", event.getUserId(), event.getEventId());
			return;
		}

		for (Booking booking : bookings) {
			if ("PAID".equalsIgnoreCase(event.getStatus())) {
				booking.setBookingStatus(BookingStatus.CONFIRMED);
				booking.setBookingPaymentstatus(BookingPaymentStatus.PAID);
			} else {
				booking.setBookingStatus(BookingStatus.CANCELLED);
				booking.setBookingPaymentstatus(BookingPaymentStatus.FAILED);
			}

			bookingRepository.save(booking);

			log.info("Booking updated: ID={}, status={}, paymentStatus={}",
					booking.getId(),
					booking.getBookingStatus(),
					booking.getBookingPaymentstatus());
		}
	}

}