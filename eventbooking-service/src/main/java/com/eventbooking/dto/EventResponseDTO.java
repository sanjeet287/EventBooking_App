package com.eventbooking.dto;

import java.time.LocalDateTime;

import com.eventbooking.entity.event_enum.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventResponseDTO {

	private Long id;
	private String title;
	private String description;
	private String location;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer totalSeats;
	private Integer availableSeats;
	private String status;
	private String organizerName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
//	private Double Price;
	private Double regularSeatPrice;
	private Double vipSeatPrice;
	private Category category;
}
