package com.pm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pm.entity.Games;


@Repository
public class GamesDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<Games> gamesList() {
		
		return this.getHibernateTemplate().find("from Games");
	}
	
}
