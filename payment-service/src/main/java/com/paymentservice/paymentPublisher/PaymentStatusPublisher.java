package com.paymentservice.paymentPublisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.paymentservice.config.RabbitMQConfigConstant;
import com.paymentservice.dto.PaymentStatusEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentStatusPublisher {

	private final RabbitTemplate rabbitTemplate;

	public void publishStatus(PaymentStatusEvent event) {
		rabbitTemplate.convertAndSend(RabbitMQConfigConstant.EXCHANGE, RabbitMQConfigConstant.ROUTING_KEY, event);
	}
}
