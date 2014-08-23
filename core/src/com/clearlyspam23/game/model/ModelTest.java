package com.clearlyspam23.game.model;

import com.clearlyspam23.game.model.Resource.ResourceType;
import com.clearlyspam23.game.model.logging.ConsoleLogger;
import com.clearlyspam23.game.model.structures.Mine;
import com.clearlyspam23.game.model.structures.SpacePort;

public class ModelTest {
	
	public static void main(String[] args){
		GameData data = new GameData();
		data.logger = new ConsoleLogger();
		
		Resource resource = new Resource("Iron", ResourceType.unrefined);
		ValueTable baseTable = new ValueTable();
		baseTable.addResource(resource, 100);
		
		Planet planet1 = new Planet("earth", baseTable, 1.0f, 1.0f, new SpacePort(5, 25));
		planet1.addStructure(new Mine(resource, 10, 50));
		data.planets.addPlanet(planet1);
		Planet planet2 = new Planet("mars", baseTable, 0.8f, 1.2f, new SpacePort(5, 25));
		data.planets.addPlanet(planet2);
		data.planets.makeThatConnection(planet1, planet2, 20);
		Planet planet3 = new Planet("jupiter", baseTable, 1.2f, 0.8f, new SpacePort(5, 25));
		data.planets.addPlanet(planet3);
		data.planets.makeThatConnection(planet2, planet3, 50);
		planet1.addTradeAgreement(new TradeAgreement(resource, planet2));
		planet1.addTradeAgreement(new TradeAgreement(resource, planet3));
		for(int i = 0 ;i < 100; i++){
			data.tick++;
			for(Ship s : data.activeShips){
				s.onTick(data);
			}
			for(Planet p : data.planets.getPlanets()){
				p.onTick(data);
			}
			for(Ship s : data.removeList){
				data.activeShips.remove(s);
			}
			data.removeList.clear();
		}
	}

}
