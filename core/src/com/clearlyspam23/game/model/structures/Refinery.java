package com.clearlyspam23.game.model.structures;

import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Structure;

public class Refinery extends Structure {
	
	private Resource base;
	private Resource result;
	private int rate;
	private int increment;
	
	public Refinery(Resource base, Resource result, int rate, int increment){
		this.base = base;
		this.result = result;
		this.rate = rate;
		this.increment = increment;
	}

	@Override
	public void performEffect(GameData data, Planet planet) {
		if(data.tick%rate==0){
			int amount = planet.removeResourceAmount(base, increment);
			if(amount<=0)
				return;
			planet.addResourceAmount(result, amount);
			data.logInfo("Converted " + amount + " " + base.name + " into " + result.name + " on " + planet.getName());
		}
	}

	@Override
	public String getName() {
		return null;
	}

}
