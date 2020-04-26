package com.talch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitMQProperties {

	private String exchangeName;
	
	private String queueNameA;
	private String queueNameB;
	private String queueNameC;
	private String queueNameD;

	private String routingKeyA;
	private String routingKeyB;
	private String routingKeyC;
	private String routingKeyD;

}
