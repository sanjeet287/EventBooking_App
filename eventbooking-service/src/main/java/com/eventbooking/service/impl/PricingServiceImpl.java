package com.eventbooking.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.eventbooking.dto.PricingRequestDTO;
import com.eventbooking.dto.PricingResponseDTO;
import com.eventbooking.entity.Pricing;
import com.eventbooking.entity.event_enum.Category;
import com.eventbooking.repository.PricingRepository;
import com.eventbooking.service.PricingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

	private final PricingRepository pricingRepository;

	private final ModelMapper modelMapper;

	@Override
	public PricingResponseDTO createOrUpdatePricing(PricingRequestDTO requestDTO) {

		Pricing pricing = pricingRepository.findByCategory(requestDTO.getCategory()).map(existing -> {
			existing.setRegularSeatPrice(requestDTO.getRegularSeatPrice());
			existing.setVipSeatPrice(requestDTO.getVipSeatPrice());
			return existing;
		}).orElseGet(() -> modelMapper.map(requestDTO, Pricing.class));

		return modelMapper.map(pricingRepository.save(pricing), PricingResponseDTO.class);
	}

	@Override
	public PricingResponseDTO getPricingByCategory(Category category) {

		Pricing pricing = pricingRepository.findByCategory(category)
				.orElseThrow(() -> new RuntimeException("Pricing not found for category " + category));
		return modelMapper.map(pricing, PricingResponseDTO.class);
	}
	
	 public PricingResponseDTO applySurgePricing(PricingRequestDTO basePricing, int totalSeats, int bookedSeats) {
	        
		 double occupancyRate = (bookedSeats * 100.0) / totalSeats;

	        double surgeMultiplier;
	        if (occupancyRate <= 60) {
	            surgeMultiplier = 1.0;
	        } else if (occupancyRate <= 80) {
	            surgeMultiplier = 1.2;
	        } else {
	            surgeMultiplier = 1.5;
	        }

	         Pricing pricing=Pricing.builder()
	            .category(basePricing.getCategory())
	            .regularSeatPrice(basePricing.getRegularSeatPrice() * surgeMultiplier)
	            .vipSeatPrice(basePricing.getVipSeatPrice() * surgeMultiplier)
	            .build();
	         
	         return modelMapper.map(pricing, PricingResponseDTO.class);
	    }

}
