/**
 * @author Talch
 *
 */

package com.talch.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.talch.beans.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

	Users findByPhoneAndPassword(String phone, String password);

	Users findByToken(String token);

}