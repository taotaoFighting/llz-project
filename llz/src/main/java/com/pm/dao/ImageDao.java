package com.pm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pm.entity.Image;

@Repository
public class ImageDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public List<Image> imageListByType(String type) {
		
		return this.getHibernateTemplate().findByNamedParam("from Image where 1 = 1 and type=:type", "type", type);
	}
}
