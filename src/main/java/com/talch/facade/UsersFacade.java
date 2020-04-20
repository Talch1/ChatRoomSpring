/**
 * @author Talch
 *
 */
package com.talch.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.talch.beans.Role;
import com.talch.beans.Users;
import com.talch.customExeption.ExExeption;
import com.talch.repo.UserRepo;
import com.talch.service.SysService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
@Scope("prototype")
public class UsersFacade implements Facade {

	@Autowired
	private UserRepo userRepo;

	private long id;

	private Role role;

	private String phone;

	private String password;

	private String name;

	@Autowired
	private SysService service;

	public String getPassword(String countryCode, String operatorCode, String userPhone, String userName)
			throws ExExeption {
		// We send the phone number and send them to Twilio and get 4 number code
		// and before we have this Mokup

		if ((userPhone.length() != 7)) {
			throw new ExExeption("This phone is invalide");
		}
		String pin = "" + ((int) (Math.random() * 9000) + 1000);
		if (service.getCountryCode().get(countryCode) != null
				&& ((service.getOperatorCode().get(operatorCode) != null))) {
			String currPhone = service.getCountryCode().get(countryCode) + service.getOperatorCode().get(operatorCode)
					+ userPhone;
			if ((currPhone.equals("+972547219582")) || (currPhone.equals("+972546647991"))) {
				userRepo.save(new Users(id++, currPhone, pin, userName, Role.Admin, null));
				return pin;
			} else
				userRepo.save(new Users(id++, currPhone, pin, userName, Role.User, null));
			return pin;
		}
		throw new ExExeption("Country code or Operator code is incorrect");
	}

	public Users getUserByToken(String token) throws ExExeption {
	
			if (userRepo.findByToken(token)!=null) {
				return userRepo.findByToken(token);
			}else {
				throw new ExExeption("User don't fund");
			}
			
	}

	public Users saveUser(Users user) {
		return userRepo.save(user);
	}
}
