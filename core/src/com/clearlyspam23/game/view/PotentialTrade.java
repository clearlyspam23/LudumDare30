package com.clearlyspam23.game.view;

import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;

public class PotentialTrade {
	public final Planet destination;
	public final Planet source;
	public final Resource resource;
	
	public PotentialTrade(Planet source, Planet planet, Resource resource){
		this.source = source;
		this.destination = planet;
		this.resource = resource;
	}
}
