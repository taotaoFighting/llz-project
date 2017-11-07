package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.entity.Category;
import com.pm.service.CategoryService;

@RestController
@RequestMapping(value="category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping(value="/",method=RequestMethod.POST)
	public Category addCategory(@RequestBody Category category) {
		
		categoryService.addCategory(category);
		return category;
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<Category> categorys() {
		
		return categoryService.list();
	}
}
