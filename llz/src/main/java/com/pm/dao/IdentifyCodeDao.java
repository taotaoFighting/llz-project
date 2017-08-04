package com.pm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pm.entity.IdentifyCode;

@Repository
public class IdentifyCodeDao extends BaseDao {

	public List<IdentifyCode> querycodeByMobile(String mobileNumber,String codeString) {
		
		String[] paramStrings = new String[]{"mobileNumber","codeString"};
		String[] valueStrings = new String[]{mobileNumber,codeString};
		
		return this.getHibernateTemplate().findByNamedParam("from IdentifyCode where 1=1 and used = 0 and mobileNumber=:mobileNumber and indentifyCode=:codeString", paramStrings, valueStrings);
	}
	
}
