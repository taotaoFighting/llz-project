package com.pm.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pm.entity.Padvice;

@Controller
public class LoginController {

	@RequestMapping(value = "/login")
	public String login() {
		
		return "login/login";
	}
	
	@RequestMapping(value = "/main")
	public String main() {
		
		return "main/main";
	}
	
	@RequestMapping(value = "/defaultPage")
	public String defaultPage() {
		
		return "defaultPage/defaultPage";
	}
	
	@RequestMapping(value = "/foodManager")
	public String foodManager() {
		
		return "foodManager/foodManager";
	}
}
