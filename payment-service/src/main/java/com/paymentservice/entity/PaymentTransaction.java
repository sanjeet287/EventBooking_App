package com.paymentservice.entity;

import java.time.LocalDateTime;

import com.paymentservice.entity.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data

public class PaymentTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String stripePaymentIntentId;

	private Long eventId;

	private Long userId;

	private Double amount;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status; // SUCCESS, FAILED, PENDING

	private LocalDateTime createdAt;
}
