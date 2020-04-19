/**
 * @author Talch
 *
 */
package com.talch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talch.customExeption.ExExeption;
import com.talch.customExeption.FacadeNullExeption;
import com.talch.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	// http://localhost:8081/room/getAll/"
	@GetMapping(value = "/getAll/")
	public ResponseEntity<?> getAll(@RequestHeader String token) {
		

		try {
			return ResponseEntity.status(HttpStatus.OK).body(roomService.getAllRooms(token));
		} catch (FacadeNullExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalide User");
	}

	// http://localhost:8081/room/createRoom/"
	@PostMapping(value = "/createRoom/")
	public ResponseEntity<?> createRoom(@RequestHeader String token, @RequestParam String roomName) {
	

		try {
			return ResponseEntity.status(HttpStatus.OK).body(roomService.createRoom(token, roomName));
		} catch (FacadeNullExeption | ExExeption e) {

			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalide User");
	}

	// http://localhost:8081/room/updateRoom/"
	@PutMapping(value = "/updateRoom/")
	public ResponseEntity<?> update(@RequestHeader String token, @RequestParam String roomName,
			@RequestParam String newRoomName) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(roomService.updateRoom(token, roomName, newRoomName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Facade = null");
	}

	@PostMapping(value = "/enterRoom/")
	public ResponseEntity<?> enterRoom(@RequestHeader String token, @RequestParam String roomName) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(roomService.enterRoom(token, roomName));
		} catch (FacadeNullExeption | ExExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalide User");
	}
}
