package com.eventbooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDTO {

	@NotNull(message = "Event ID is mandatory")
	private Long eventId;

	@NotNull(message = "Number of Seats must be provided")
	private Integer numberOfSeats;

	private String seatType; // REGULAR, VIP

}
