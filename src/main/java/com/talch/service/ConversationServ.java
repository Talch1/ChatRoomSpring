package com.talch.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.talch.beans.ChattingMessage;
import com.talch.beans.Room;
import com.talch.beans.Users;
import com.talch.config.RabbitMQProperties;
import com.talch.rabbit.Producer;
import com.talch.repo.RoomRepo;


@Service
public class ConversationServ {
	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired 
	private RoomRepo roomRepo;
	
	@Autowired
	private ChattingMessage message;
	
	public void start(long id) {
		Optional<Room> room =roomRepo.findById(id);
		Producer producer = ctx.getBean(Producer.class);
		RabbitMQProperties propert = ctx.getBean(RabbitMQProperties.class);
		
		propert.setExchangeName("talch");
		propert.setQueueName("talch");
		propert.setRoutingKey("talch");
		
		producer.sendMessage(new ChattingMessage("start chat", null,
				System.currentTimeMillis(),room.get().getId()));
		ArrayList<Users> userList = new ArrayList<Users>();
		for (Users user :room.get().getUsers()) {
			userList.add(user);
			
		}
		producer.sendMessage(new ChattingMessage("user " + userList.get(0).getUserName() +
				" connected", userList.get(0).getUserName(),
				System.currentTimeMillis(), room.get().getId()));

		producer.sendMessage(new ChattingMessage("user " + userList.get(1).getUserName() +
				" connected", userList.get(1).getUserName(),
				System.currentTimeMillis(), room.get().getId()));
	}

}
