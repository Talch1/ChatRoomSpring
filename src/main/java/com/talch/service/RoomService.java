/**
 * @author Talch
 *
 */
package com.talch.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import com.talch.beans.Room;
import com.talch.beans.RoomColor;
import com.talch.beans.Users;
import com.talch.customExeption.ExExeption;
import com.talch.customExeption.FacadeNullExeption;
import com.talch.facade.UsersFacade;
import com.talch.repo.RoomRepo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomService {

	public static long roomNum = 1;
	public static long convId = 1;

	@Autowired
	private RoomRepo roomRepo;
	
	@Autowired
	private SysService service;

	@Autowired
	private UsersFacade userFacade;
	
	@Autowired 
	private ApplicationContext ctx;

	@Autowired
	private ConversationServ convServ;
	
	@PostConstruct
	public void roomInilaice() {
		
		roomRepo.deleteAll();
		roomRepo.save(new Room(roomNum++, RoomColor.Green, "room1", null));
		roomRepo.save(new Room(roomNum++, RoomColor.Green, "room2", null));
		roomRepo.save(new Room(roomNum++, RoomColor.Green, "room3", null));

	}

	public Collection<Room> getAllRooms(String token) throws ExExeption, FacadeNullExeption {
		
		Users user = userFacade.getUserByToken(token);
		service.userCheck(user);
		return roomRepo.findAll();
	}

	public Room getRoomByRoomName(String roomName) {
		
		return roomRepo.findByRoomName(roomName);
	}

	public Optional<Room> getRoomById(long id) {
		
		return roomRepo.findById(id);
	}

	public Room createRoom(String token, String roomName) throws FacadeNullExeption, ExExeption {
		
		Users user = userFacade.getUserByToken(token);
		service.userCheck(user);
		Room room = roomRepo.findByRoomName(roomName);
		
		if (room == null) {
			return roomRepo.save(new Room(roomNum++, RoomColor.Green, roomName, null));
		}
		throw new ExExeption("This room is exist");
	}

	public Room updateRoom(String token, String roomName, String newRoomName) throws FacadeNullExeption, ExExeption {
		
		Room room = getRoomByRoomName(roomName);
		Users user = userFacade.getUserByToken(token);
		service.userCheck(user);
		room.setRoomName(newRoomName);
		
		return roomRepo.save(new Room(room.getId(), room.getColor(), newRoomName, room.getUsers()));
	}

	public Room enterRoom(String token, long id) throws FacadeNullExeption, ExExeption {
		
		Optional<Room> room = getRoomById(id);
		Users user = userFacade.getUserByToken(token);
		
		if (room.get().getColor().equals(RoomColor.Green)) {
			
			Collection<Users> userSet = new ArrayList<Users>();
			userSet.add(user);
			room.get().setUsers(userSet);
			room.get().setColor(RoomColor.Yellow);
			return roomRepo.save(room.get());
			
		} else if (room.get().getColor().equals(RoomColor.Yellow)) {

			Collection<Users> userSet = room.get().getUsers();
			userSet.add(user);
			room.get().setUsers(userSet);
			room.get().setColor(RoomColor.Red);
			convServ.start(room.get().getId());
			
			return roomRepo.save(room.get());
		}
		throw new ExExeption("This room is full");
	}

	public Room roomExit(String token, long id) throws ExExeption {
		
		Optional<Room> room = getRoomById(id);
		Users user = userFacade.getUserByToken(token);
		
		switch (room.get().getColor().toString()) {
		
		case ("Green"):
			throw new ExExeption("Room is empty");
		
		case ("Yellow"):
			
			Collection<Users> roomUsers1 = room.get().getUsers();
			roomUsers1.remove(user);
			System.out.println("user " + user + "came out");
			room.get().setColor(RoomColor.Green);
			room.get().setUsers(roomUsers1);
			roomRepo.save(room.get());
			return room.get();
			
		case ("Red"):
			
			Collection<Users> roomUsers = room.get().getUsers();
			roomUsers.remove(user);
			System.out.println("user " + user + "came out");
			room.get().setColor(RoomColor.Yellow);
			room.get().setUsers(roomUsers);
			roomRepo.save(room.get());
			return room.get();
			
		default: return null;
		}
	}

}