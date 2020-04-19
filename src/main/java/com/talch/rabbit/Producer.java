package com.talch.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.talch.beans.ChattingMessage;
import com.talch.config.RabbitMQProperties;
import com.talch.service.SysService;

@Component
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitMQProperties rabbitMQProperties;
    @Autowired
	SysService service;   


	public void sendMessage(ChattingMessage message) {
		service.getMemoryChatMap().put(message.getRoom(), message);
		  rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getRoutingKey(), message.getMessage());
	System.out.println(message);
	}
}