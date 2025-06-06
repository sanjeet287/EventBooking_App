package com.eventbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventbooking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByEventId(Long eventId);

	List<Booking> findByUserId(Long userId);

	List<Booking> findByEventIdAndUserId(Long eventId, Long userId);

}
