package com.clearlyspam23.game.model;

import java.util.List;

import com.clearlyspam23.game.model.Logger.MessageType;

public class Ship {
	
	public final Resource resource;
	public final int amount;
	public final List<Planet> path;
	
	public int distance;
	
	public Ship(Resource resource, int amount, List<Planet> path, GameData data){
		this.resource = resource;
		this.amount = amount;
		this.path = path;
		Planet current = path.remove(0);
		data.logInfo("Ship leaves "+ current.getName());
		distance = data.planets.getDistanceToPlanet(current, path.get(0));
	}
	
	public void onTick(GameData data){
		distance-=1;
		if(distance<=0){
			Planet current = path.remove(0);
			data.logger.log(data.tick, "Ship arrives at "+ current.getName(), MessageType.info);
			if(path.isEmpty()){
				int value = (int) (current.getResourceBuyingValue(resource)*amount);
				current.addResourceAmount(resource, amount);
				data.amountOfMoney+=(value);
				data.logInfo("" + amount + " " + resource.name + " sold to " + current.getName() + " for " + value);
			}
			else{
				distance = data.planets.getDistanceToPlanet(current, path.get(0));
			}
			data.removeList.add(this);
		}
	}

}
