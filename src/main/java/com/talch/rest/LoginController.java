/**
 * @author Talch
 *
 */
package com.talch.rest;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talch.ChatSystem;
import com.talch.beans.Role;
import com.talch.beans.Users;
import com.talch.customExeption.FacadeNullExeption;
import com.talch.facade.Facade;
import com.talch.facade.UsersFacade;
import com.talch.service.SysService;

@RestController
@RequestMapping("login")
public class LoginController {
	
	private static long id = 1;
	
	@Autowired
	private ChatSystem system;
	
	@Autowired
	private UsersFacade userFacade;

	@Autowired
	private SysService service;

	@PostConstruct
	public void start() {
		System.out.println("start");
		Users user1 = new Users(1, "+972548012831", "1111", "Rus", Role.User, null);
		Users user2 = new Users(21, "+972548012832", "1112", "Tus", Role.User, null);
		Users user3 = new Users(31, "+972548012833", "1113", "Sam", Role.User, null);
		Users user4 = new Users(41, "+972548012834", "1114", "Gai", Role.User, null);
		userFacade.getUserRepo().save(user1);
		userFacade.getUserRepo().save(user2);
		userFacade.getUserRepo().save(user3);
		userFacade.getUserRepo().save(user4);

	}
	// http://localhost:8081/logging/{phone}/{password}
	@PostMapping(value = "/logging/")
	public ResponseEntity<?> login(@RequestParam("countryCode") String countryCode,
			@RequestParam("operatorCode") String operatorCode, @RequestParam("phone") String phone,
			@RequestParam("password") String password) {

		CustomSession session = new CustomSession();
		Facade facade = null;
		// String token = UUID.randomUUID().toString();
		String token = "user" + id++;
		long lastAccessed = System.currentTimeMillis();

		String userPhone;
		userPhone = service.getCountryCode().get(countryCode) + service.getOperatorCode().get(operatorCode) + phone;
		try {
			facade = system.login(userPhone, password);
		} catch (FacadeNullExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (facade != null) {
			Users user = userFacade.getUserRepo().findByPhoneAndPassword(userPhone, password);
			user.setToken(token);
			userFacade.saveUser(user);
//		    session.setFacade(facade);
//		    session.setLastAccessed(lastAccessed);
//			system.getSessionList().add(session);
//			System.out.println(system.getSessionList());
			return ResponseEntity.status(HttpStatus.OK).body(token);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalide User");
		}

	}

	// http://localhost:8081/getPassword/{phone}
	@PostMapping(value = "/getPassword/")
	public ResponseEntity<?> getPassword(@RequestParam("countryCode") String countryCode,
			@RequestParam("operatorCode") String operatorCode, @RequestParam("phone") String phone,
			@RequestParam("userName") String userName) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(userFacade.getPassword(countryCode, operatorCode, phone, userName));
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone is invalide");
	}
}