package com.talch.beans;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.talch.config.RabbitMQProperties;
import com.talch.rabbit.Producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Scope("prototype")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Conversation {
	
	@Autowired
	private ChattingMessage message;
	
	private Room room;
	
	@Autowired
	ApplicationContext ctx;

	public void start() {
		
		Producer producer = ctx.getBean(Producer.class);
		RabbitMQProperties propert = ctx.getBean(RabbitMQProperties.class);
		
		propert.setExchangeName("talch");
		propert.setQueueName("talch");
		propert.setRoutingKey("talch");
		
		producer.sendMessage(new ChattingMessage("start chat", null, System.currentTimeMillis(),room.getId()));
		ArrayList<Users> userList = new ArrayList<Users>();
		for (Users user : room.getUsers()) {
			userList.add(user);
			
		}
		producer.sendMessage(new ChattingMessage("user " + userList.get(0).getUserName() + " connected", userList.get(0).getUserName(),
				System.currentTimeMillis(), room.getId()));

		producer.sendMessage(new ChattingMessage("user " + userList.get(1).getUserName() + " connected", userList.get(1).getUserName(),
				System.currentTimeMillis(), room.getId()));
	}

}
