package com.clearlyspam23.game.model;

public interface GameEventListener {
	
	public void onShipAdd(Ship ship, GameData data);
	
	public void onMoneyChange(int amount, Planet sourcePlanet, GameData data);
	
//	public void onPlanetAdd(Planet planet, GameData data);

}
