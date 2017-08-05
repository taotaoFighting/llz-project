package com.pm.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.pm.entity.Users;

@Repository
public class UsersDao extends BaseDao {
	
	public List<Users> queryUserByUserNameAndPassword(Users user) {
		
		String[] params = new String[]{"username","password"};
		
		String[] values = new String[]{user.getUsername(),user.getPassword()};
		
		return this.getHibernateTemplate().findByNamedParam("from Users where 1=1 and username=:username and password=:password",params,values);
	}

}
