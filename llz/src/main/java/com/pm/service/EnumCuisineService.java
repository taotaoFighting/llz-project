package com.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.dao.EnumCuisineDao;
import com.pm.entity.EnumCuisine;

@Service("enum")
public class EnumCuisineService {
	
	@Autowired
	EnumCuisineDao enumCuisineDao;

	//菜系枚举
	public List<EnumCuisine> cuisineList() {
		
		return enumCuisineDao.cuisineList();
		
	}
}