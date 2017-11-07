package com.pm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pm.entity.Category;

@Repository
public class CategoryDao extends BaseDao {
	
	@SuppressWarnings("unchecked")
	public List<Category> list() {
		return this.getHibernateTemplate().find("from Category");
	}
	
	public void updateCategory(Category category) {
		
		this.getHibernateTemplate().update(category);
	}

}
