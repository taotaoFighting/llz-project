package com.pm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pm.entity.Dictionary;

@Repository
public class DictionaryDao extends BaseDao {
	@SuppressWarnings("unchecked")
	public List<Dictionary> list() {
		return this.getHibernateTemplate().find("from Dictionary");
	}
}
