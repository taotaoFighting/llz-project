package com.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.dao.ImageDao;
import com.pm.entity.Image;

@Service("imageService")
public class ImageService {

	@Autowired
	ImageDao imageDao;
	
	public List<Image> imageListByType(String type) {
		return imageDao.imageListByType(type);
	}
}
