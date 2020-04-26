package com.talch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talch.beans.ChattingMessage;
import com.talch.service.ConversationServ;

@RestController
@RequestMapping("/send")
public class MessageController {

	@Autowired
	private ConversationServ serv;

	@PostMapping(value = "/producer")
	public ChattingMessage producer(@RequestBody String message,
			@RequestHeader String token) {
		return serv.sendMessage(message, token);
	}
}
