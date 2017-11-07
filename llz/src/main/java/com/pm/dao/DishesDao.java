package com.pm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pm.entity.Dishes;


@Repository
public class DishesDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<Dishes> dishesList() {
		
		return this.getHibernateTemplate().find("from Dishes");
	}
	
@SuppressWarnings("unchecked")
public List<Dishes> dishesListByCuidineId(String cuisineId) {
	
	System.out.print("cuisineId = "+cuisineId + ".");
		
		return this.getHibernateTemplate().findByNamedParam("from Dishes where 1=1 and cuisineId =:cuisineId","cuisineId",cuisineId);
	}
}
