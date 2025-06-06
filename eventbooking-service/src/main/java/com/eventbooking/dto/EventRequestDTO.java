package com.eventbooking.dto;

import java.time.LocalDateTime;

import com.eventbooking.entity.event_enum.Category;
import com.eventbooking.entity.event_enum.Status;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventRequestDTO {

	@NotBlank(message = "Title is required")
	private String title;

	@NotBlank(message = "Description is required")
	private String description;

	@NotBlank(message = "Location is required")
	private String location;

	// @NotNull(message = "Start time is required")
	@Future(message = "Start time must be in the future")
	private LocalDateTime startTime = LocalDateTime.now();

	// @NotNull(message = "End time is required")
	@Future(message = "End time must be in the future")
	private LocalDateTime endTime = LocalDateTime.now();;

	private Integer availableSeats;

	private Status status;

	private LocalDateTime updatedAt;

	@NotNull(message = "Total seats are required")
	@Min(value = 1, message = "Total seats must be at least 1")
	private Integer totalSeats;

	@NotNull(message = "Category is required")
	private Category category;
	
	private Double Price;
	
	private Double regularSeatPrice;
	
	private Double vipSeatPrice;

}
