package com.talch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "admin-prop")
@Data
public class AdminConfig {

	private String firstAdminPhone;
	private String secondAdminPhone;

	private String firstAdminName;
	private String secondAdminName;

	private String adminPass;

}
