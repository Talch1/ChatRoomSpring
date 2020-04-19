package com.talch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talch.customExeption.ExExeption;
import com.talch.customExeption.FacadeNullExeption;
import com.talch.facade.AdminFacade;

@RestController
@RequestMapping("/admin")
public class AdminRoomController {

	@Autowired
	private AdminFacade adminFacade;

	// http://localhost:8081/admin/roomDelete"
	@GetMapping(value = "/roomDelete/")
	public ResponseEntity<?> getAll(@RequestHeader String token, @RequestParam String roomName) {
		adminFacade.getUserRepo().findByToken(token);
		try {

			return ResponseEntity.status(HttpStatus.OK).body(adminFacade.deleteRoom(token, roomName));
		} catch (FacadeNullExeption e) {
			ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getStackTrace());
		} catch (ExExeption e) {
			ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getStackTrace());
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalide User");
	}

}
