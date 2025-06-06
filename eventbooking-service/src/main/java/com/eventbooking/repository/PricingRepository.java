package com.eventbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventbooking.entity.Pricing;
import com.eventbooking.entity.event_enum.Category;

@Repository
public interface PricingRepository extends JpaRepository<Pricing, Long>{
	
	Optional<Pricing> findByCategory(Category category);

}
