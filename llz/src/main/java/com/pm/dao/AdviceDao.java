package com.pm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pm.entity.Padvice;

@Repository
public class AdviceDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<Padvice> allAdvices() {
		
		return this.getHibernateTemplate().find("from Padvice");
	}
}
