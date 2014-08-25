package com.clearlyspam23.game.model.structures;

import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Structure;

public class Refinery extends BaseRefinery {

	public Refinery(Resource base, Resource result, int rate, int increment){
		super(base, result, rate, increment, "Refinery", 2000, 9000, 25000);
	}
	
	@Override
	public Structure copy() {
		return new Refinery(base, result, rate, increment);
	}

}
