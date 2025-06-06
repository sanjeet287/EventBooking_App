package com.paymentservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservice.dto.PaymentConfirmationDTO;
import com.paymentservice.dto.PaymentRequestDTO;
import com.paymentservice.dto.PaymentResponseDTO;
import com.paymentservice.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments/stripe")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/initate")
	public ResponseEntity<PaymentResponseDTO> process(@RequestBody PaymentRequestDTO dto) {
		return ResponseEntity.ok(paymentService.initiatePayment(dto));
	}

	@PostMapping("/confirm")
	public ResponseEntity<String> confirm(@RequestBody PaymentConfirmationDTO dto) {
		paymentService.confirmPayment(dto);
		return ResponseEntity.ok("Payment confirmed");
	}

}
