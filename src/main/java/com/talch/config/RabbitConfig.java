package com.talch.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class RabbitConfig {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	RabbitMQProperties rabroper;

	@Bean
	Queue queue1() {
		return new Queue(rabroper.getQueueNameA(), false);
	}

	@Bean
	Queue queue2() {
		return new Queue(rabroper.getQueueNameB(), false);
	}

	@Bean
	Queue queue3() {
		return new Queue(rabroper.getQueueNameC(), false);
	}

	@Bean
	Queue queue4() {
		return new Queue(rabroper.getQueueNameD(), false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(rabroper.getExchangeName());
	}

	@Bean
	Binding bindingA(Queue queue1, TopicExchange exchange) {
		return BindingBuilder.bind(queue1).to(exchange).with(rabroper.getRoutingKeyA());
	}

	@Bean
	Binding bindingB(Queue queue2, TopicExchange exchange) {
		return BindingBuilder.bind(queue2).to(exchange).with(rabroper.getRoutingKeyB());
	}

	@Bean
	Binding bindingC(Queue queue3, TopicExchange exchange) {
		return BindingBuilder.bind(queue3).to(exchange).with(rabroper.getRoutingKeyC());
	}

	@Bean
	Binding bindingD(Queue queue4, TopicExchange exchange) {
		return BindingBuilder.bind(queue4).to(exchange).with(rabroper.getRoutingKeyD());
	}

	@Bean
	public SimpleMessageListenerContainer simpleRabbitListener1(ConnectionFactory connectionFactory, Queue queue1) {
		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
		listenerContainer.addQueues(queue1);
		listenerContainer.setMessageListener(
				message -> System.out.println("Received message A " + new String(message.getBody())));
		return listenerContainer;
	}

	@Bean
	public SimpleMessageListenerContainer simpleRabbitListener2(ConnectionFactory connectionFactory, Queue queue2) {
		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
		listenerContainer.addQueues(queue2);
		listenerContainer.setMessageListener(
				message -> System.out.println("Received message B " + new String(message.getBody())));
		return listenerContainer;
	}

	@Bean
	public SimpleMessageListenerContainer simpleRabbitListener3(ConnectionFactory connectionFactory, Queue queue3) {
		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
		listenerContainer.addQueues(queue3);
		listenerContainer.setMessageListener(
				message -> System.out.println("Received message C " + new String(message.getBody())));
		return listenerContainer;
	}

	@Bean
	public SimpleMessageListenerContainer simpleRabbitListener4(ConnectionFactory connectionFactory, Queue queue4) {
		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
		listenerContainer.addQueues(queue4);
		listenerContainer.setMessageListener(
				message -> System.out.println("Received message D " + new String(message.getBody())));
		return listenerContainer;
	}

}
