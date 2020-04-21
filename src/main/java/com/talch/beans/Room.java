/**
 * @author Talch
 *
 */
package com.talch.beans;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "RoomTable")
public class Room {
	
	private long id;
	
	private RoomColor color;
	
	private String roomName;
	
	@Autowired
	private Collection<Users> users;
	
	@Id
	@Column(unique = true, nullable = false,name = "roomId")
	public long getId() {
		return id;
	}
	
	@Enumerated(EnumType.STRING)
	public RoomColor getColor() {
		return color;
	}
	
	@OneToMany( cascade=CascadeType.ALL)
	public Collection<Users> getUsers() {
		return users;
	}

}