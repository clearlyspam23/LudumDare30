package com.clearlyspam23.game.model;

import java.util.ArrayList;
import java.util.List;

import com.clearlyspam23.game.model.Logger.MessageType;

public class GameData {
	
	public int tick;
	
	public long amountOfMoney;
	
	public Logger logger;
	
	public PlanetGrid planets = new PlanetGrid();
	
	public List<Ship> activeShips = new ArrayList<Ship>();
	public List<Ship> removeList = new ArrayList<Ship>();
	
	public void logInfo(String message){
		logger.log(tick, message, MessageType.info);
	}

}
