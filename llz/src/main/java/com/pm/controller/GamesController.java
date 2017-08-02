package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.entity.Games;
import com.pm.service.GamesService;

@RestController()
@RequestMapping(value="/games")
public class GamesController {
	
	@Autowired
	GamesService gamesService;
	
	@RequestMapping(value="/game_all",method=RequestMethod.GET)
	public List<Games> games() {
		
		return gamesService.gamesList();
	}

}
