package com.eventbooking.dto;

import com.eventbooking.entity.event_enum.Category;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingResponseDTO {
	
    private Category category;
    private Double regularSeatPrice;
    private Double vipSeatPrice;
}