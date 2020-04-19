package com.talch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talch.ChatSystem;
import com.talch.beans.ChattingMessage;
import com.talch.rabbit.Producer;

@RestController
@RequestMapping("/send")
public class MessageController {
	

@Autowired
Producer producer;
	
	@Autowired
	ChatSystem system;

	@PostMapping(value = "/mess")
	public ResponseEntity<?> send(/*@RequestHeader String token,*/@RequestBody ChattingMessage  message) {
		
//		CustomSession sesion = system.getTokensMap().get(token);
//		message.setUser(sesion.getFacade().getName());
//		
		message.setTime(System.currentTimeMillis());
//		try {
			producer.sendMessage(message /*sesion*/);
//		} catch (FacadeNullExeption e) {
//
//			e.printStackTrace();
//		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
}
}
