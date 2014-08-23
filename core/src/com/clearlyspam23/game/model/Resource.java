package com.clearlyspam23.game.model;

public class Resource {
	
	public enum ResourceType{
		unrefined, refined, manufactured, artisan
	}
	
	public final String name;
	
	public final ResourceType type;
	
	public Resource(String name, ResourceType type){
		this.name = name;
		this.type = type;
	}

}
