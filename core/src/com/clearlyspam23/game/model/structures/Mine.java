package com.clearlyspam23.game.model.structures;

import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Structure;

public class Mine extends Structure{
	
	private Resource resource;
	private int increment;
	private int rate;
	
	public Mine(Resource resource, int rate, int increment){
		this.resource = resource;
		this.rate = rate;
		this.increment = increment;
	}

	@Override
	public void performEffect(GameData data, Planet planet) {
		if(data.tick%rate==0){
			planet.addResourceAmount(resource, increment);
			data.logInfo("" + increment + " " + resource.name + " added to " + planet.getName() + " from " + getName());
		}
	}

	@Override
	public String getName() {
		return resource.name + " Mine";
	}

	public Resource getResource() {
		return resource;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

}
