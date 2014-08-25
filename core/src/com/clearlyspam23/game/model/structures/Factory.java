package com.clearlyspam23.game.model.structures;

import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Structure;

public class Factory extends BaseRefinery {

	public Factory(Resource base, Resource result, int rate, int increment) {
		super(base, result, rate, increment, "Factory", 20000, 60000, 150000);
	}

	@Override
	public Structure copy() {
		return new Factory(base, result, rate, increment);
	}

}
