package com.clearlyspam23.game.model.structures;

import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Structure;

public class Manufactory extends BaseRefinery{

	public Manufactory(Resource base, Resource result, int rate, int increment) {
		super(base, result, rate, increment, "Manufactory",100000, 200000, 400000);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Structure copy() {
		return new Manufactory(base, result, rate, increment);
	}

}
