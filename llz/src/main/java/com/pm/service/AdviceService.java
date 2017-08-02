package com.pm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.dao.AdviceDao;
import com.pm.entity.Padvice;

@Service("adviceService")
public class AdviceService {

	@Autowired
	AdviceDao adviceDao;
	
	public void addAdvice(Padvice padvice) {
		
		padvice.setUpdateDate(new Date());
		
		adviceDao.addEntity(padvice);
	}
	
	public List<Padvice> allAdvices() {
		
		return adviceDao.allAdvices();
	}
}
