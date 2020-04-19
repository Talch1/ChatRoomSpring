package com.talch.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.talch.beans.ChattingMessage;
import com.talch.beans.Users;
import com.talch.customExeption.FacadeNullExeption;
import com.talch.facade.Facade;

import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
public class SysService {

	private Map<String, String> countryCode = new HashMap<String, String>();

	private Map<String, String> operatorCode = new HashMap<String, String>();
	
	private Map<String, ChattingMessage> memoryChatMap = new HashMap<String, ChattingMessage>();

	@PostConstruct
	public void setPhoneNumbers() {
		countryCode.put("Isr", "+972");
		countryCode.put("Ukr", "+38");
		countryCode.put("FR", "+33");
		countryCode.put("Gr", "+30");
		countryCode.put("It", "+39");
		countryCode.put("UK", "+44");

		operatorCode.put("Pel", "50");
		operatorCode.put("We4", "51");
		operatorCode.put("Cel", "52");
		operatorCode.put("Hot", "53");
		operatorCode.put("Par", "54");
		operatorCode.put("Ram", "55");
		operatorCode.put("Gol", "58");

	}

	public boolean facadeCheck(Facade facade) throws FacadeNullExeption {
		if (facade !=null) {
			return true;
		}
		throw new FacadeNullExeption("Facade is null");
	}
	public boolean userCheck(Users user) throws FacadeNullExeption {
		if (user !=null) {
			return true;
		}
		throw new FacadeNullExeption("User is null");
	}
}
