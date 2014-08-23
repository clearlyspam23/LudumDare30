package com.clearlyspam23.game.model;

import java.util.List;

import com.clearlyspam23.game.model.Logger.MessageType;

public class Ship {
	
	public final Resource resource;
	public final int amount;
	public final List<Planet> path;
	
	public int distanceLeft;
	public int totalDistance;
	
	public final Planet startingPlanet;
	public final Planet destinationPlanet;
	
	private Planet currentPlanet;
	
	public Ship(Resource resource, int amount, List<Planet> path, GameData data, Planet planet){
		this.resource = resource;
		this.amount = amount;
		this.path = path;
		Planet current = path.remove(0);
		currentPlanet = startingPlanet = planet;
		destinationPlanet = path.get(path.size()-1);
		data.logInfo("Ship leaves "+ current.getName() + " with " + amount + " " + resource.name + " headed for " + destinationPlanet.getName());
		totalDistance = distanceLeft = data.getPlanetGrid().getDistanceToPlanet(current, path.get(0));
	}
	
	public void onTick(GameData data){
		distanceLeft-=1;
		if(distanceLeft<=0){
			currentPlanet = path.remove(0);
			data.logger.log(data.tick, "Ship arrives at "+ currentPlanet.getName(), MessageType.info);
			if(path.isEmpty()){
				int value = (int) (currentPlanet.getResourceBuyingValue(resource)*amount);
				currentPlanet.addResourceAmount(resource, amount);
				data.changeAmountOfMoney(value, destinationPlanet);
//				data.setAmountOfMoney(data.getAmountOfMoney() + (value));
				data.logInfo("" + amount + " " + resource.name + " sold to " + currentPlanet.getName() + " for " + value);
			}
			else{
				currentPlanet.getSpacePort().addWaitingShip(this);
				totalDistance = distanceLeft = data.getPlanetGrid().getDistanceToPlanet(currentPlanet, path.get(0));
			}
			data.removeList.add(this);
		}
	}
	
	public Planet getNextVisit(){
		return path.get(0);
	}
	
	public Planet getCurrentPlanet(){
		return currentPlanet;
	}

}
