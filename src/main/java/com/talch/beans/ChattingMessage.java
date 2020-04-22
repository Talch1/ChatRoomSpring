package com.talch.beans;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Document
public class ChattingMessage {

    private String message;
    private String user;
    private long time = System.currentTimeMillis();
    private long roomId;
    
    
	public String getMessage() {
		return message;
	}
	public String getUser() {
		return user;
	}
	public long getTime() {
		return time;
	}
	@Id
	public long getRoomId() {
		return roomId;
	}

}