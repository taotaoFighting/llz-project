package com.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pm.dao.GamesDao;
import com.pm.entity.Games;

@Service("gameService")
public class GamesService {
	
	@Autowired
	GamesDao gamesDao;

	@Transactional
	public List<Games> gamesList() {
		
		return gamesDao.gamesList();
	}
	
}
