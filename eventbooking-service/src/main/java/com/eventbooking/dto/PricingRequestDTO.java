package com.eventbooking.dto;

import com.eventbooking.entity.event_enum.Category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PricingRequestDTO {
    private Category category;
    private Double regularSeatPrice;
    private Double vipSeatPrice;
}