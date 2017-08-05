package com.pm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pm.entity.EnumCuisine;

@Repository
public class EnumCuisineDao extends BaseDao {

	//菜系全枚举
	public List<EnumCuisine> cuisineList() {
		
		return this.getHibernateTemplate().find("from EnumCuisine");
		
	}
}
