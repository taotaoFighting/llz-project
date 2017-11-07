package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.entity.Category;
import com.pm.entity.Dictionary;
import com.pm.service.DictionaryService;

@RestController
@RequestMapping(value="dict")
public class DictionaryController {
	
	@Autowired
	DictionaryService dictionaryService;
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Dictionary addCategory(@RequestBody Dictionary category) {
		
		dictionaryService.addDict(category);
		return category;
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<Dictionary> dicts() {
		
		return dictionaryService.list();
	}

}
