package com.eventbooking.entity;

import java.time.LocalDateTime;

import com.eventbooking.entity.event_enum.BookingPaymentStatus;
import com.eventbooking.entity.event_enum.BookingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer numberOfSeats;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder.Default
	private LocalDateTime bookingTime = LocalDateTime.now();

	private String seatType; // REGULAR / VIP

	private Double totalAmount;

	// private Status booking;

	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;

	@Enumerated(EnumType.STRING)
	private BookingPaymentStatus bookingPaymentstatus; // CREATED / PAID / CANCELLED

	@Override
	public String toString() {
		return "Booking [id=" + id + ", numberOfSeats=" + numberOfSeats + ", event=" + event + ", user=" + user
				+ ", bookingTime=" + bookingTime + ", seatType=" + seatType + ", totalAmount=" + totalAmount
				+ ", bookingStatus=" + bookingStatus + ", bookingPaymentstatus=" + bookingPaymentstatus + "]";
	}
	
	

}
