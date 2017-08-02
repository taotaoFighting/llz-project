package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.entity.Padvice;
import com.pm.service.AdviceService;

@RestController
public class Adviceontroller {
	
	@Autowired
	AdviceService adviceService;
	
	@RequestMapping(value = "/advice",method = RequestMethod.POST)
	public Padvice addAdvice(@RequestBody Padvice padvice) {
		
		adviceService.addAdvice(padvice);
		
		return padvice;
	}
	
	
	
	
	@RequestMapping(value = "/advice",method = RequestMethod.GET)
	public List<Padvice> allAdvices() {
		
		return adviceService.allAdvices();
	}
	
}
