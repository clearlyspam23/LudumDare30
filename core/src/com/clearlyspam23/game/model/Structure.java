package com.clearlyspam23.game.model;

public abstract class Structure {
	
	private int planetValue;
	
	private int buyingValue;
	
	private int[] upgradeCosts;
	
	private int level;
	
	public Structure(int... costs){
		buyingValue = costs[0];
		upgradeCosts = new int[costs.length-1];
		for(int i = 0; i < upgradeCosts.length; i++){
			upgradeCosts[i] = costs[i+1];
		}
	}

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
	
	public boolean canUpgrade(){
		return level<upgradeCosts.length;
	}
	
	public int getNextUpgradeCost(){
		return upgradeCosts[level];
	}
	
	public int getLevel(){
		return level;
	}
	
	public void incrementLevel(){
		level++;
	}
	
	public abstract Structure copy();

	public abstract void performEffect(GameData data, Planet planet);
	
	public abstract String getName();

}
