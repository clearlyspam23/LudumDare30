package com.clearlyspam23.game.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.clearlyspam23.game.model.Logger.MessageType;

public class GameData {
	
	public int tick;
	
	private long amountOfMoney;
	
	public Logger logger;
	
	private PlanetGrid planets = new PlanetGrid();
	
	private List<Ship> activeShips = new ArrayList<Ship>();
	public List<Ship> removeList = new ArrayList<Ship>();
	private List<GameEventListener> listeners = new ArrayList<GameEventListener>();
	
	private Set<Planet> ownedPlanets = new HashSet<Planet>();
	
	public void logInfo(String message){
		logger.log(tick, message, MessageType.info);
	}

	public List<Ship> getActiveShips() {
		return activeShips;
	}

	public void addShip(Ship ship) {
		activeShips.add(ship);
		for(GameEventListener l : listeners)
			l.onShipAdd(ship, this);
	}
	
	public void addListener(GameEventListener listener){
		listeners.add(listener);
	}

	public long getAmountOfMoney() {
		return amountOfMoney;
	}
	
	public void changeAmountOfMoney(int amount){
		changeAmountOfMoney(amount, null);
	}

	public void changeAmountOfMoney(int amount, Planet source) {
		this.amountOfMoney+=amount;
		for(GameEventListener l : listeners)
			l.onMoneyChange(amount, source, this);
	}
	
	public void doTick(){
		tick++;
		for(Ship s : activeShips){
			s.onTick(this);
		}
		for(Planet p : planets.getPlanets()){
			p.onTick(this);
		}
		for(Ship s : removeList){
			getActiveShips().remove(s);
		}
		removeList.clear();
	}

	public Set<Planet> getOwnedPlanets() {
		return ownedPlanets;
	}

	public void setOwnedPlanets(Set<Planet> ownedPlanets) {
		this.ownedPlanets = ownedPlanets;
	}
	
	public void addPlanet(Planet planet){
		planets.addPlanet(planet);
//		for(GameEventListener l : listeners)
//			l.onPlanetAdd(planet, this);
	}
	
	public PlanetGrid getPlanetGrid(){
		return planets;
	}

}
