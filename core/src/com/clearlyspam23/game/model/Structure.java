package com.clearlyspam23.game.model;

public abstract class Structure {
	
	private int planetValue;
	
	private int buyingValue;

	public int getPlanetValue() {
		return planetValue;
	}

	public void setPlanetValue(int planetValue) {
		this.planetValue = planetValue;
	}

	public int getBuyingValue() {
		return buyingValue;
	}

	public void setBuyingValue(int buyingValue) {
		this.buyingValue = buyingValue;
	}

	public abstract void performEffect(GameData data, Planet planet);
	
	public abstract String getName();

}
