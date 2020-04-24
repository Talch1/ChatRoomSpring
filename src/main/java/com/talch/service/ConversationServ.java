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
import com.talch.repo.RoomRepo;
import com.talch.repo.UserRepo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationServ {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private RoomRepo roomRepo;

	@Autowired
	private UserRepo userRepo;
	@Autowired
	Producer producer;
	
	public void sendFirstConnection(long id) {
		
		Optional<Room> room = roomRepo.findById(id);
		Producer producer = ctx.getBean(Producer.class);
		RabbitMQProperties propert = ctx.getBean(RabbitMQProperties.class);
		ChattingMessage chattingMessage = ctx.getBean(ChattingMessage.class);
		ChattingMessage chattingMessage2 = ctx.getBean(ChattingMessage.class);
		
		ArrayList<Users> userList = new ArrayList<Users>();
		for (Users user : room.get().getUsers()) {
			userList.add(user);
		}
		
		chattingMessage.setMessage("user " + userList.get(0).getUserName() + " connected");
		chattingMessage.setRoomId(room.get().getId());
		chattingMessage.setTime(System.currentTimeMillis());
		chattingMessage.setUser(userList.get(0).getUserName());
		
		producer.sendMessage(chattingMessage, propert.getExchangeName(), propert.getRoutingKey());
		
		chattingMessage2.setMessage("user " + userList.get(1).getUserName() + " connected");
		chattingMessage2.setRoomId(room.get().getId());
		chattingMessage2.setTime(System.currentTimeMillis());
		chattingMessage2.setUser(userList.get(1).getUserName());

		producer.sendMessage(chattingMessage2, propert.getExchangeName(), propert.getRoutingKey());
	}

	public void start(long id) {
		Optional<Room> room = roomRepo.findById(id);
		Producer producer = ctx.getBean(Producer.class);
		RabbitMQProperties propert = ctx.getBean(RabbitMQProperties.class);
		ChattingMessage chattingMessage = ctx.getBean(ChattingMessage.class);

		chattingMessage.setMessage("start chat");
		chattingMessage.setRoomId(room.get().getId());
		chattingMessage.setTime(System.currentTimeMillis());
		chattingMessage.setUser("System");

		producer.sendMessage(chattingMessage, propert.getExchangeName(), propert.getRoutingKey());
		
		sendFirstConnection(room.get().getId());	}

	public ChattingMessage sendMessage(String message, String exchange, String routingKey, String token) {
		ChattingMessage chattingM = ctx.getBean(ChattingMessage.class);
		Users user = userRepo.findByToken(token);
		Room room = roomRepo.findRoomByUsers(user);
		chattingM.setUser(user.getUserName());
		chattingM.setTime(System.currentTimeMillis());
		chattingM.setMessage(message);
		chattingM.setRoomId(room.getId());
		return producer.sendMessage(chattingM, exchange, routingKey);

	}

}
