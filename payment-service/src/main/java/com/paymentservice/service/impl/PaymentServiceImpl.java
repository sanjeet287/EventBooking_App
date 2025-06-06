package com.paymentservice.service.impl;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paymentservice.config.RabbitMQConfigConstant;
import com.paymentservice.dto.PaymentConfirmationDTO;
import com.paymentservice.dto.PaymentRequestDTO;
import com.paymentservice.dto.PaymentResponseDTO;
import com.paymentservice.dto.PaymentStatusEvent;
import com.paymentservice.entity.PaymentTransaction;
import com.paymentservice.entity.enums.PaymentStatus;
import com.paymentservice.paymentPublisher.PaymentStatusPublisher;
import com.paymentservice.repository.PaymentRepository;
import com.paymentservice.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	private final RabbitTemplate rabbitTemplate;

	private final PaymentStatusPublisher publisher;

	@Value("${stripe.secret.key}")
	private String stripeSecretKey;

	@PostConstruct
	public void init() {
		Stripe.apiKey = stripeSecretKey;
	}

	@Override
	@Transactional
	public PaymentResponseDTO initiatePayment(PaymentRequestDTO dto) {

		log.info("Processing payment for userId: {}, eventId: {}", dto.getUserId(), dto.getEventId());

		try {
			// 1. Create Stripe PaymentIntent
			PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
					.setAmount((long) (dto.getAmount() * 100)).setCurrency("inr")
					.setAutomaticPaymentMethods(
							PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
					.build();

			PaymentIntent intent = PaymentIntent.create(params);

			PaymentTransaction txn = new PaymentTransaction();
			txn.setAmount(dto.getAmount());
			txn.setEventId(dto.getEventId());
			txn.setUserId(dto.getUserId());
			txn.setStripePaymentIntentId(intent.getId());
			txn.setStatus(PaymentStatus.INTIATED);
			txn.setCreatedAt(LocalDateTime.now());

			// save transaction to DB
			paymentRepository.save(txn);

			// 3. Notify booking
			publisher.publishStatus(new PaymentStatusEvent(dto.getUserId(), dto.getEventId(), intent.getId(), "PAID"));

			return new PaymentResponseDTO(intent.getClientSecret(), "Payment initiated");

		} catch (Exception e) {
			log.error("Stripe error: {}", e.getMessage());

			// Save failed transaction
			PaymentTransaction failedTx = new PaymentTransaction();
			failedTx.setUserId(dto.getUserId());
			failedTx.setEventId(dto.getEventId());
			failedTx.setAmount(dto.getAmount());
			failedTx.setStatus(PaymentStatus.FAILED);
			failedTx.setCreatedAt(LocalDateTime.now());

			paymentRepository.save(failedTx);

			// Notify booking service of failure
			publisher.publishStatus(new PaymentStatusEvent(dto.getUserId(), dto.getEventId(), null, "FAILED"));

			log.info("Payment failed and event sent to booking service.");

			throw new RuntimeException("Payment initiation failed");
		}
	}

	@Transactional
	public void confirmPayment(PaymentConfirmationDTO dto) {
		log.info("Confirming payment with intent: {}", dto.getPaymentIntentId());

		PaymentTransaction txn = paymentRepository.findByStripePaymentIntentId(dto.getPaymentIntentId())
				.orElseThrow(() -> new RuntimeException("Transaction not found"));

		if (dto.isPaymentSuccess()) {
			txn.setStatus(PaymentStatus.SUCCESS);
			// Send event to BookingService to mark booking as CONFIRMED
			rabbitTemplate.convertAndSend("booking.exchange", "payment.confirmed", dto);
		} else {
			txn.setStatus(PaymentStatus.FAILED);
		}

		// save failed txn to DB
		paymentRepository.save(txn);

		// Notify booking service
		rabbitTemplate.convertAndSend(RabbitMQConfigConstant.QUEUE, RabbitMQConfigConstant.ROUTING_KEY, dto);

		log.info("Payment confirmed and event sent to booking service.");
	}

}
