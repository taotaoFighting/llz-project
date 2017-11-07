package com.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.dao.CategoryDao;
import com.pm.entity.Category;

@Service("CategoryService")
public class CategoryService {
	
	@Autowired
	CategoryDao categoryDao;
	
	public void addCategory(Category category) {
		
		categoryDao.addEntity(category);
	}
	
	public List<Category> list() {
		
		return categoryDao.list();
	}

	public void updateCategory() {
		
		
	}
}
