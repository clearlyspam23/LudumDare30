package com.clearlyspam23.game.model;

import java.util.List;

public class Ship {
	
	public final Resource resource;
	public final int amount;
	public final int rate;
	public final List<Planet> path;
	
	public Ship(Resource resource, int amount, int rate, List<Planet> path){
		this.resource = resource;
		this.amount = amount;
		this.rate = rate;
		this.path = path;
	}

}
