package com.clearlyspam23.game.model.structures;

import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Structure;

public abstract class BaseRefinery extends Structure {
	
	protected Resource base;
	protected Resource result;
	protected int rate;
	protected int increment;
	private String name;
	
	protected BaseRefinery(Resource base, Resource result, int rate, int increment, String name, int... values){
		super(values);
		this.base = base;
		this.result = result;
		this.rate = rate;
		this.increment = increment;
		this.name = name;
	}

	@Override
	public void performEffect(GameData data, Planet planet) {
		if(data.tick%(rate-getLevel()*2)==0){
			int amount = planet.removeResourceAmount(base, increment);
			if(amount<=0)
				return;
			planet.addResourceAmount(result, amount);
			data.logInfo("Converted " + amount + " " + base.name + " into " + result.name + " on " + planet.getName());
		}
	}

	@Override
	public String getName() {
		return result.name + " " + name;
	}

	@Override
	public abstract Structure copy();
	
	public Resource getBase(){
		return base;
	}
	
	public Resource getResult(){
		return result;
	}
	
	public boolean equals(Object other){
		if(!(other instanceof BaseRefinery))
			return false;
		BaseRefinery refinery = (BaseRefinery) other;
		return refinery.base.equals(base)&&refinery.result.equals(result);
	}
	
	public int hashCode(){
		return base.hashCode()+result.hashCode();
	}

}
