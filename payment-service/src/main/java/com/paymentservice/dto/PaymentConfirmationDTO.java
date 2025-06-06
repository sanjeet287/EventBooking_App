package com.paymentservice.dto;

import lombok.Data;

@Data
public class PaymentConfirmationDTO {

	private String paymentIntentId;

	private Long eventId;

	private Long userId;

	private boolean  isPaymentSuccess;
}
