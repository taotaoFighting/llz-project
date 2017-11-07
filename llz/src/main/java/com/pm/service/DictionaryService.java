package com.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.dao.CategoryDao;
import com.pm.dao.DictionaryDao;
import com.pm.entity.Category;
import com.pm.entity.Dictionary;

@Service("DictionaryService")
public class DictionaryService {
	
	@Autowired
	DictionaryDao dictionaryDao;
	
	public void addDict(Dictionary dict) {
		
		dictionaryDao.addEntity(dict);
	}
	
	public List<Dictionary> list() {
		
		return dictionaryDao.list();
	}

}
