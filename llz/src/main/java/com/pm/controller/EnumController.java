package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.entity.EnumCuisine;
import com.pm.service.EnumCuisineService;

@RestController
@RequestMapping(value="/enum")
public class EnumController {
	
	@Autowired
	EnumCuisineService enumCuisineService;

	@RequestMapping(value="/cuisine/list")
	public List<EnumCuisine> cuisineList() {
		
		return enumCuisineService.cuisineList();
	}
}
