package com.clearlyspam23.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Planet {
	
	private final String name;
	
	private final ValueTable valueTable;
	private float sellingFavor;
	private float buyingFavor;
	
	private Map<Resource, Integer> availableResources = new HashMap<Resource, Integer>();
	
	private int basePrice;
	
	private List<Structure> purchasedStructures = new ArrayList<Structure>();
	
	public Planet(String name, ValueTable table, float buying, float selling){
		this.name = name;
		this.valueTable = table;
		buyingFavor = buying;
		sellingFavor = selling;
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

	public List<Structure> getPurchasedStructures() {
		return purchasedStructures;
	}

	public void addStructure(Structure structure){
		purchasedStructures.add(structure);
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

}
