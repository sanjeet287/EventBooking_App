package com.eventbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiationEvent implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
    private Long eventId;
    private Double amount;
    private Integer numberOfSeats;
    
	@Override
	public String toString() {
		return "PaymentInitiationEvent [userId=" + userId + ", eventId=" + eventId + ", amount=" + amount
				+ ", numberOfSeats=" + numberOfSeats + "]";
	}
}
