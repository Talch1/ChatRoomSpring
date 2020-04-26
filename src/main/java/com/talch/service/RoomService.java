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

import com.talch.beans.ChattingMessage;
import com.talch.beans.Room;
import com.talch.beans.RoomColor;
import com.talch.beans.Users;
import com.talch.customExeption.ExExeption;
import com.talch.customExeption.FacadeNullExeption;
import com.talch.facade.UsersFacade;
import com.talch.repo.ChatRepo;
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

	@Autowired
	private ChatRepo chatRepo;

	@Autowired
	private Producer producer;

	@PostConstruct
	public void roomInilaice() {

		roomRepo.deleteAll();
		roomRepo.save(new Room(roomNum++, null, RoomColor.Green, "room1", null));
		roomRepo.save(new Room(roomNum++, null, RoomColor.Green, "room2", null));
		roomRepo.save(new Room(roomNum++, null, RoomColor.Green, "room3", null));

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
			return roomRepo.save(new Room(roomNum++, null, RoomColor.Green, roomName, null));
		}
		throw new ExExeption("This room is exist");
	}

	public Room updateRoom(String token, String roomName, String newRoomName) throws FacadeNullExeption, ExExeption {

		Room room = getRoomByRoomName(roomName);
		Users user = userFacade.getUserByToken(token);
		service.userCheck(user);
		room.setRoomName(newRoomName);

		return roomRepo.save(new Room(room.getId(), room.getRoutKey(), room.getColor(), newRoomName, room.getUsers()));
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
			try {
				convServ.start(room.get().getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return roomRepo.save(room.get());
		}
		throw new ExExeption("This room is full");
	}

	public Room exitRoom(String token, long id) throws ExExeption {

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

			try {
				convServ.blockingQueue.put(room.get().getRoutKey());
				room.get().setRoutKey(null);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			roomRepo.save(room.get());
			ArrayList<ChattingMessage> chattingMessages = (ArrayList<ChattingMessage>) producer.getAllMessages()
					.clone();
			chattingMessages.removeIf(m -> m.getRoomId() != room.get().getId());
			chatRepo.saveAll(chattingMessages);

			return room.get();

		case ("Red"):

			Collection<Users> roomUsers = room.get().getUsers();
			roomUsers.remove(user);
			System.out.println("user " + user + "came out");
			room.get().setColor(RoomColor.Yellow);
			room.get().setUsers(roomUsers);
			roomRepo.save(room.get());
			return room.get();

		default:
			return null;
		}
	}

}