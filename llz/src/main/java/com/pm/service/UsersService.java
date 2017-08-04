package com.pm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.dao.UsersDao;
import com.pm.entity.Users;

@Service("userService")
public class UsersService {
	
	@Autowired
	UsersDao usersDao;
	
	public void saveUser(Users user) {
		
		usersDao.addEntity(user);
		
	}

}
