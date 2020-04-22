package com.talch.rest;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talch.beans.ChattingMessage;
import com.talch.service.MessageService;

@RestController
@RequestMapping("/send")
public class MessageController {

	@Autowired
	MessageService messageService;


	@PostMapping(value = "/producer")
	public ChattingMessage producer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") 
	String routingKey,
	@RequestBody ChattingMessage message,@RequestHeader String token) {
       
		messageService.sendMessage(message,exchange,routingKey);
		
		return message;
	}
}
