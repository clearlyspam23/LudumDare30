package com.clearlyspam23.game.model;

public class Resource {
	
	public enum ResourceType{
		unrefined, refined, manufactured, artisan
	}
	
	private String name;
	
	private ResourceType tier;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResourceType getTier() {
		return tier;
	}

	public void setTier(ResourceType tier) {
		this.tier = tier;
	}

}
