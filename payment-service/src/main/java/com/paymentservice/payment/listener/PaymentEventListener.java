package com.paymentservice.payment.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.paymentservice.config.RabbitMQConfigConstant;
import com.paymentservice.dto.PaymentRequestDTO;
import com.paymentservice.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

	private final PaymentService paymentService;

	@RabbitListener(queues = RabbitMQConfigConstant.QUEUE)
	public void handlePaymentRequest(PaymentRequestDTO dto) {
		log.info("Received payment request from BookingService: {}", dto);
		paymentService.initiatePayment(dto);
	}
}
