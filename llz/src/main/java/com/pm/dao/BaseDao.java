package com.pm.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



public class BaseDao extends HibernateDaoSupport {

	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory){
		
		super.setSessionFactory(sessionFactory);
	}
	
	public <T> void addEntity(T entity) {
		
		System.out.print(this.getHibernateTemplate().save(entity));
	}
	
	public <T> void updateEntity(T entity) {
		
		this.getHibernateTemplate().update(entity);
	}

	public <T> void deleteEntity(T entity) {
	
	this.getHibernateTemplate().delete(entity);
	}
}
