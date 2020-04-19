package com.talch.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author talch
 *
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "userTable")
public class Users {

	private long id;

	private String phone;

	private String password;

	private String userName;

	private Role role;

	String token;
	
	@Id
	@Column(unique = true, nullable = false, name = "userPhone")
	public String getPhone() {
		return phone;
	}

	@Column(unique = true, nullable = false, name = "userId")
	public long getId() {
		return id;
	}

	
	@Column(name = "userPassword")
	public String getPassword() {
		return password;
	}

	@Column(name = "token")
	public String getToken() {
		return token;
	}

	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return role;
	}

}
