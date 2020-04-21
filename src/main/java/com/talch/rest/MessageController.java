package com.talch.rest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talch.ChatSystem;
import com.talch.beans.ChattingMessage;
import com.talch.rabbit.Producer;

@RestController
@RequestMapping("/send")
public class MessageController {
	

	@Autowired
	private RabbitTemplate rabbitTemplate;



	@GetMapping(value = "/producer")
	public String producer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,
			@RequestParam("messageData") String messageData) {

		rabbitTemplate.convertAndSend(exchange, routingKey, messageData);

		return "Message sent to the RabbitMQ Topic Exchange Successfully";
	}
}
