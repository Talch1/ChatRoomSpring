package com.talch.beans;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ChattingMessage {

    private String message;
    private String user;
    private long time = System.currentTimeMillis();
    private long roomId;

}