package com.paymentservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Bean
	public TopicExchange paymentExchange() {
		return new TopicExchange(RabbitMQConfigConstant.EXCHANGE);
	}

	@Bean
	public Queue paymentQueue() {
		return new Queue(RabbitMQConfigConstant.QUEUE, true);
	}

	@Bean
	public Binding paymentBinding(Queue paymentQueue, TopicExchange paymentExchange) {
		return BindingBuilder.bind(paymentQueue).to(paymentExchange).with(RabbitMQConfigConstant.ROUTING_KEY);
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter());
		return template;
	}
}
