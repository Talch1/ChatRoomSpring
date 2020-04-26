
package com.talch.service;

import java.util.ArrayList;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.talch.beans.ChattingMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class Producer {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	ApplicationContext ctx;

	private ArrayList<ChattingMessage> allMessages = new ArrayList<ChattingMessage>();

	public ChattingMessage sendMessage(String exchange, String routingKey,ChattingMessage message) {

		allMessages.add(message);
		System.out.println("ex "+exchange+" : " +" rout " + routingKey);
		rabbitTemplate.convertAndSend(exchange, routingKey, message.getUser() + ": " + message);
		return message;
	}
}