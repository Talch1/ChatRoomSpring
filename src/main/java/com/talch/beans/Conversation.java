package com.talch.beans;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
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

	private String roomName;
	@Autowired
	private ChattingMessage message;

	private Collection<Users> users;
	@Autowired
	ApplicationContext ctx;

	public void start() {
		Producer producer = ctx.getBean(Producer.class);
		RabbitMQProperties propert = ctx.getBean(RabbitMQProperties.class);
		System.out.println("AAA");
		propert.setExchangeName("talch");
		propert.setQueueName("talch");
		propert.setRoutingKey("talch");
		producer.sendMessage(new ChattingMessage("start chat", null, System.currentTimeMillis(), "talch"));
//		producer.sendMessage(new ChattingMessage("user " + users.get(0) + " connected", users.get(0).getUserName(),
//				System.currentTimeMillis(), roomName));
//
//		producer.sendMessage(new ChattingMessage("user " + users.get(1) + " connected", users.get(1).getUserName(),
//				System.currentTimeMillis(), roomName));
	}

}
