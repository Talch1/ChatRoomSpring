package com.talch.facade;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talch.beans.Role;
import com.talch.beans.Room;
import com.talch.beans.Users;
import com.talch.customExeption.ExExeption;
import com.talch.customExeption.FacadeNullExeption;
import com.talch.repo.RoomRepo;
import com.talch.repo.UserRepo;
import com.talch.service.SysService;

import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Data
public class AdminFacade implements Facade {

	@Autowired
	private RoomRepo roomRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private SysService service;

	private long id;

	private String phone;

	private String name;

	private String password;

	private Role role = Role.Admin;

	@PostConstruct
	public void setRepo() {
		userRepo.deleteAll();
	}

	public Collection<Room> deleteRoom(String token, String roomName) throws FacadeNullExeption, ExExeption {
		Users user = userRepo.findByToken(token);
		
		if (user != null) {
			if (user.getRole().toString().equals("Admin")) {
				Room room = roomRepo.findByRoomName(roomName);
				if (room!=null) {
					roomRepo.delete(room);	
					return roomRepo.findAll();
				}else {
					throw new ExExeption("this room is not exist");
				}
				 
			}else {
				throw new FacadeNullExeption("Just admin can delete Room");
			}
			
		}
	throw new FacadeNullExeption("Facade is null");
		
	}

}
