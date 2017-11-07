package com.pm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.dao.DishesDao;
import com.pm.entity.Dishes;

@Service("dishesService")
public class DishesService {

	@Autowired
	DishesDao dishesDao;
	
	public List<Dishes> dishesList() {
		
		return dishesDao.dishesList();
	}
	
	public List<Dishes> dishesListByCuisineId(String cuisineId) {
		
		return dishesDao.dishesListByCuidineId(cuisineId);
	}
	
public void addDishes(Dishes dishes) {
		dishes.setCreateTime(new Date());
		dishesDao.addEntity(dishes);
	}

public void upadteDishes(Dishes dishes) {
	dishesDao.updateEntity(dishes);
}
}
