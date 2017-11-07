package com.pm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.entity.Users;
import com.pm.service.IdentifyCodeService;
import com.pm.service.UsersService;
import com.pm.util.Status;

@RestController
@RequestMapping(value="/user")
public class UsersController {
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	IdentifyCodeService identifyCodeService;
	
	@RequestMapping(value="/signUp",method=RequestMethod.POST)
	public Status signUp(@RequestBody Users user) {
		Status status = new Status();
		if(identifyCodeService.isUsed(user.getUsername(), user.getIdentifyCode())){
			usersService.saveUser(user);
			status.setData(user);
			status.setMsg("sucess");
			status.setStatus(200);
			return status;
		}
		status.setMsg("验证码不存在或已使用");
		status.setStatus(200);
		return status;
	}
	
	@RequestMapping(value="/signIn",method=RequestMethod.POST)
	public Status signIn(@RequestBody Users user,HttpSession session) {
		Status s = usersService.queryUserByUserNameAndPassword(user);
		if(s.getStatus() == 1){
			session.setAttribute("userName", user.getUsername());
		}
		return s;
				
	}

}
