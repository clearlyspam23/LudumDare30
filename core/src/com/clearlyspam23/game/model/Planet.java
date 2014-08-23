package com.clearlyspam23.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clearlyspam23.game.model.structures.SpacePort;

public class Planet {
	
	private final String name;
	
	private final ValueTable valueTable;
	private float sellingFavor;
	private float buyingFavor;
	
	private Map<Resource, Integer> availableResources = new HashMap<Resource, Integer>();
	
	private int basePrice;
	
	private List<Structure> structures = new ArrayList<Structure>();
	private List<Planet> connectedPlanets = new ArrayList<Planet>();
	private List<TradeAgreement> activeTrades = new ArrayList<TradeAgreement>();
	
	private SpacePort spacePort;
	
	public Planet(String name, ValueTable table, float buying, float selling, SpacePort port){
		this.name = name;
		this.valueTable = table;
		buyingFavor = buying;
		sellingFavor = selling;
		spacePort = port;
		structures.add(spacePort);
	}

	public String getName() {
		return name;
	}

	public Map<Resource, Integer> getResources() {
		return availableResources;
	}
	
	public void addResourceAmount(Resource resource, int amount){
		if(availableResources.containsKey(resource))
			amount+=availableResources.get(resource);
		availableResources.put(resource, amount);
	}
	
	public int removeResourceAmount(Resource resource, int maximumAmount){
		if(!availableResources.containsKey(resource))
			return 0;
		int current = availableResources.get(resource);
		int amount = Math.min(current, maximumAmount);
		availableResources.put(resource, current-amount);
		return amount;
	}
	
	public int getResourceAmount(Resource resource){
		if(!availableResources.containsKey(resource))
			return 0;
		return availableResources.get(resource);
	}

	public int getBuyingPrice() {
		return basePrice;
	}

	public void setBasePrice(int buyingPrice) {
		this.basePrice = buyingPrice;
	}

	public List<Structure> getStructures() {
		return structures;
	}

	public void addStructure(Structure structure){
		structures.add(structure);
	}

	public ValueTable getValueTable() {
		return valueTable;
	}

	public float getBuyingFavor() {
		return buyingFavor;
	}

	public void setBuyingFavor(float buyingFavor) {
		this.buyingFavor = buyingFavor;
	}

	public float getSellingFavor() {
		return sellingFavor;
	}

	public void setSellingFavor(float sellingFavor) {
		this.sellingFavor = sellingFavor;
	}
	
	public float getResourceBuyingValue(Resource resource){
		return buyingFavor*valueTable.getValueOf(resource);
	}
	
	public float getResourceSellingValue(Resource resource){
		return sellingFavor*valueTable.getValueOf(resource);
	}
	
	public void onTick(GameData data){
		for(Structure structure : structures){
			structure.performEffect(data, this);
		}
	}
	
	public List<Planet> getConnectedPlanets(){
		return connectedPlanets;
	}
	
	public void makeThatConnection(Planet planet){
		connectedPlanets.add(planet);
	}

	public List<TradeAgreement> getActiveTrades() {
		return activeTrades;
	}

	public void addTradeAgreement(TradeAgreement agreement) {
		activeTrades.add(agreement);
	}
	
	public void removeTradeAgreement(TradeAgreement agreement){
		activeTrades.remove(agreement);
	}
	
	public SpacePort getSpacePort(){
		return spacePort;
	}

}
