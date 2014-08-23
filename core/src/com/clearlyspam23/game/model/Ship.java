package com.clearlyspam23.game.model;

import java.util.List;

public class Ship {
	
	public final Resource resource;
	public final int amount;
	public final int rate;
	public final List<Planet> path;
	
	public int distance;
	
	public Ship(Resource resource, int amount, int rate, List<Planet> path){
		this.resource = resource;
		this.amount = amount;
		this.rate = rate;
		this.path = path;
	}
	
	public void act(GameData data){
		distance+=1;
		if(distance>=rate){
			Planet current = path.remove(0);
			data.logger.log("Ship visits " + current.getName());
			if(path.isEmpty()){
				int value = (int) (current.getResourceBuyingValue(resource)*amount);
				data.amountOfMoney+=(value);
				data.logger.log("" + amount + resource.getName() + " sold to " + current.getName() + " for " + value);
			}
		}
	}

}
