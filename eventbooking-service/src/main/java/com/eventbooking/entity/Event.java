package com.eventbooking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.eventbooking.entity.event_enum.Category;
import com.eventbooking.entity.event_enum.Status;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	private String location;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Integer totalSeats;

	private Integer availableSeats;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organizer_id")
	private User organizer;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status; // PENDING, APPROVED, REJECTED

	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

	@Builder.Default
	private LocalDateTime updatedAt = LocalDateTime.now();

	private Double regularSeatPrice;

	private Double vipSeatPrice;

	@Enumerated(EnumType.STRING)
	private Category category;

}
