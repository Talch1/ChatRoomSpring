package com.talch.rest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.talch.facade.Facade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope("prototype")
public class CustomSession {

	private Facade facade;

	private long lastAccessed;
}
