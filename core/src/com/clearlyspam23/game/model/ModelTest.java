package com.clearlyspam23.game.model;

import com.clearlyspam23.game.model.Resource.ResourceType;
import com.clearlyspam23.game.model.logging.ConsoleLogger;
import com.clearlyspam23.game.model.structures.Mine;
import com.clearlyspam23.game.model.structures.BaseRefinery;
import com.clearlyspam23.game.model.structures.Refinery;
import com.clearlyspam23.game.model.structures.SpacePort;

public class ModelTest {
	
	public static void main(String[] args){
		GameData data = new GameData();
		data.logger = new ConsoleLogger();
		
		Resource iron = new Resource("Iron", ResourceType.unrefined);
		Resource steel = new Resource("Steel", ResourceType.refined);
		ValueTable baseTable = new ValueTable();
		baseTable.addResource(iron, 100);
		baseTable.addResource(steel, 150);
		
		Planet planet1 = new Planet("Earth", baseTable, 1.0f, 1.0f, new SpacePort(5, 25));
		planet1.addStructure(new Mine(iron, 10, 50));
		data.getPlanetGrid().addPlanet(planet1);
		Planet planet2 = new Planet("Mars", baseTable, 0.8f, 1.2f, new SpacePort(15, 50));
		planet2.addStructure(new Refinery(iron, steel, 20, 10));
		data.getPlanetGrid().addPlanet(planet2);
		data.getPlanetGrid().makeThatConnection(planet1, planet2, 20);
		Planet planet3 = new Planet("Jupiter", baseTable, 1.2f, 0.8f, new SpacePort(5, 25));
		data.getPlanetGrid().addPlanet(planet3);
		data.getPlanetGrid().makeThatConnection(planet2, planet3, 50);
		planet1.addTradeAgreement(new TradeAgreement(iron, planet2));
		planet1.addTradeAgreement(new TradeAgreement(iron, planet3));
		planet2.addTradeAgreement(new TradeAgreement(steel, planet1));
		for(int i = 0 ;i < 100; i++){
			data.doTick();
		}
	}

}
