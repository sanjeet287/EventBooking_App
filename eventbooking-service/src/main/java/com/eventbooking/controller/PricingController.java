package com.eventbooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventbooking.dto.PricingRequestDTO;
import com.eventbooking.dto.PricingResponseDTO;
import com.eventbooking.entity.event_enum.Category;
import com.eventbooking.service.PricingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/pricing")
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;

    @PostMapping("/edit/price")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PricingResponseDTO> createOrUpdatePricing(@RequestBody PricingRequestDTO dto) {
        return ResponseEntity.ok(pricingService.createOrUpdatePricing(dto));
    }

    @GetMapping("/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PricingResponseDTO> getPricing(@PathVariable Category category) {
        return ResponseEntity.ok(pricingService.getPricingByCategory(category));
    }
}

