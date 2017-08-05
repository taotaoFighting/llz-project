package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.entity.Dishes;
import com.pm.service.DishesService;

@RestController
@RequestMapping(value="/dishes")
public class DishesController {

	@Autowired
	DishesService dishesService;
	
	@RequestMapping(value="/dishesList",method=RequestMethod.GET)
	public List<Dishes> dishesList() {
		
		return dishesService.dishesList();
	}
	
	@RequestMapping(value="/dishesListBycuisineId/{id}",method=RequestMethod.GET)
	public List<Dishes> dishesListByCuisineId(@PathVariable("id") String cuisineId) {
		
		return dishesService.dishesListByCuisineId(cuisineId);
		
	}
}
