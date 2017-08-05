package com.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.dao.UsersDao;
import com.pm.entity.Users;
import com.pm.util.Status;

@Service("userService")
public class UsersService {
	
	@Autowired
	UsersDao usersDao;
	
	public void saveUser(Users user) {
		
		usersDao.addEntity(user);
		
	}
	
	public Status queryUserByUserNameAndPassword(Users user) {
		
		Status status = new Status();
		
		List<Users> list = usersDao.queryUserByUserNameAndPassword(user);
		
		if (list.size() == 0) {
			
			status.setMsg("账号或密码错误");
			status.setStatus(0);
			
			return status;
		}
		status.setMsg("登陆成功");
		status.setStatus(1);
		status.setObject(list.get(0));
		
		return status;
		
	}

}
