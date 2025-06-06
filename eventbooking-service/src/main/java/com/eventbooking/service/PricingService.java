package com.eventbooking.service;

import com.eventbooking.dto.PricingRequestDTO;
import com.eventbooking.dto.PricingResponseDTO;
import com.eventbooking.entity.event_enum.Category;

public interface PricingService {

	PricingResponseDTO createOrUpdatePricing(PricingRequestDTO requestDTO);

	PricingResponseDTO getPricingByCategory(Category category);

	PricingResponseDTO applySurgePricing(PricingRequestDTO basePricing, int totalSeats, int bookedSeats);

}
