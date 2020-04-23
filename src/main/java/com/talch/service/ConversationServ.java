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

@Service
public class ConversationServ  extends Thread{

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private RoomRepo roomRepo;

	@Autowired
	private ChattingMessage message;

	@Autowired
	private UserRepo userRepo;
	@Autowired
	Producer producer;

	public void start(long id) {
		Optional<Room> room = roomRepo.findById(id);
		Producer producer = ctx.getBean(Producer.class);
		RabbitMQProperties propert = ctx.getBean(RabbitMQProperties.class);
		ChattingMessage chattingMessage = ctx.getBean(ChattingMessage.class);

		chattingMessage.setMessage("start chat");
		chattingMessage.setRoomId(room.get().getId());
		chattingMessage.setTime(System.currentTimeMillis());
		chattingMessage.setUser(null);

		producer.sendMessage(chattingMessage, propert.getExchangeName(), propert.getRoutingKey());
		ArrayList<Users> userList = new ArrayList<Users>();
		for (Users user : room.get().getUsers()) {
			userList.add(user);
		}
		chattingMessage.setMessage("user " + userList.get(0).getUserName() + " connected");
		chattingMessage.setRoomId(room.get().getId());
		chattingMessage.setTime(System.currentTimeMillis());
		chattingMessage.setUser(userList.get(0).getUserName());

		producer.sendMessage(chattingMessage, propert.getExchangeName(), propert.getRoutingKey());

		chattingMessage.setMessage("user " + userList.get(1).getUserName() + " connected");
		chattingMessage.setRoomId(room.get().getId());
		chattingMessage.setTime(System.currentTimeMillis());
		chattingMessage.setUser(userList.get(1).getUserName());

		producer.sendMessage(chattingMessage, propert.getExchangeName(), propert.getRoutingKey());

	}

	public ChattingMessage sendMessage(ChattingMessage message, String exchange, String routingKey, String token) {

		Users user = userRepo.findByToken(token);
		message.setUser(user.getUserName());
		message.setTime(System.currentTimeMillis());
		return producer.sendMessage(message, exchange, routingKey);

	}

}
