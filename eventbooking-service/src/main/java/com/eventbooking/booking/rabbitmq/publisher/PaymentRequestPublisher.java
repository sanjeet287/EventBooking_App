package com.eventbooking.booking.rabbitmq.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.eventbooking.config.RabbitMQConfigConstant;
import com.eventbooking.dto.PaymentInitiationEvent;

@Component
@RequiredArgsConstructor
public class PaymentRequestPublisher {

	private final RabbitTemplate rabbitTemplate;

	public void publishPaymentRequest(PaymentInitiationEvent event) {
		rabbitTemplate.convertAndSend(RabbitMQConfigConstant.EXCHANGE, 
				RabbitMQConfigConstant.ROUTING_KEY, event);
	}
}
