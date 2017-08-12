package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.entity.Image;
import com.pm.service.ImageService;

@RestController
public class ImageController {

	@Autowired
	ImageService imageService;
	
	@RequestMapping(value="/image/{type}",method=RequestMethod.GET)
	public List<Image> listByType(@PathVariable("type") String type) {
		
		return imageService.imageListByType(type);
	}
}
