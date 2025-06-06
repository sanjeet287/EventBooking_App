package com.eventbooking.dto;

import lombok.Data;

@Data
public class BookingResponseDTO {
	
	private Long bookingId;
	
    private Long eventId;
    
    private Long userId;
    
    private Integer numberOfSeats;
    
    //private String status; 
    
    private String bookingStatus;
    
    private String bookingPayment;
    
    private Double totalAmount;
    
    private String seatType;
}
