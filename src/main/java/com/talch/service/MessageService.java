package com.talch.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.talch.beans.ChattingMessage;
import com.talch.repo.MongoRepo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MessageService {

	@Autowired
	ChattingMessage message;
	
	@Autowired
	MongoRepo mongoRepo;
	
	Map<Long, ChattingMessage> messagesMap = new HashMap<Long, ChattingMessage>();

	public ChattingMessage sendMessage(ChattingMessage message, String exchange, String routingKey) {
        
		messagesMap.put(message.getRoomId(), message);
		return message;
		
	}
}