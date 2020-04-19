package com.talch.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talch.beans.Room;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long>{

	Room findByRoomName(String roomName);

}
