/**
 * @author Talch
 *
 */
package com.talch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.talch.beans.Role;
import com.talch.beans.Users;
import com.talch.customExeption.FacadeNullExeption;
import com.talch.facade.AdminFacade;
import com.talch.facade.Facade;
import com.talch.facade.UsersFacade;
import com.talch.repo.UserRepo;
import com.talch.rest.CustomSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatSystem {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private UserRepo repo;
	@Autowired
	private List<CustomSession> sessionList;

	public Facade login(String phone, String password) throws FacadeNullExeption {

		if ((phone.equals("+972547219582")&&(password.equals("1234")))
				|| (phone.equals("+972546647991")&&(password.equals("1234")))) {
			AdminFacade admin = ctx.getBean(AdminFacade.class);
			admin.setPhone(phone);
			admin.setPassword(password);
			admin.setName(repo.findByPhoneAndPassword(phone, password).getUserName());
			admin.setId(repo.findByPhoneAndPassword(phone, password).getId());
			admin.setRole(repo.findByPhoneAndPassword(phone, password).getRole());
			return admin;
		} else {
			Users user = repo.findByPhoneAndPassword(phone, password);
			System.err.println(user);
			if ((user != null) && (user.getRole().equals(Role.User))) {
				UsersFacade usersFacade = ctx.getBean(UsersFacade.class);
				usersFacade.setPhone(phone);
				usersFacade.setPassword(password);
				usersFacade.setName(repo.findByPhoneAndPassword(phone, password).getUserName());
				usersFacade.setId(repo.findByPhoneAndPassword(phone, password).getId());
				usersFacade.setRole(repo.findByPhoneAndPassword(phone, password).getRole());
				return usersFacade;
			}
		}
		throw new FacadeNullExeption("Invaled User");
	}
}