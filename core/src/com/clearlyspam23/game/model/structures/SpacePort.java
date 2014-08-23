package com.clearlyspam23.game.model.structures;

import java.util.LinkedList;
import java.util.List;

import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Ship;
import com.clearlyspam23.game.model.Structure;
import com.clearlyspam23.game.model.TradeAgreement;

public class SpacePort extends Structure {
	
	private int rate;
	private int capacity;
	private int lastIndex = 0;
	
	private List<Ship> waitingShips = new LinkedList<Ship>();
	
	private boolean fireWaiting = false;
	
	public SpacePort(int rate, int capacity){
		this.rate = rate;
		this.capacity = capacity;
	}

	@Override
	public void performEffect(GameData data, Planet planet) {
		if(data.tick%rate==0&&hasTasks(planet)){
			if(fireWaiting&&!waitingShips.isEmpty()){
				data.activeShips.add(waitingShips.remove(0));
			}
			else{
				int count = 0;
				while(count<planet.getActiveTrades().size()){
					if(lastIndex>=planet.getActiveTrades().size())
						lastIndex = 0;
					TradeAgreement agreement = planet.getActiveTrades().get(lastIndex++);
					int amount = planet.removeResourceAmount(agreement.resource, capacity);
					if(amount<=0){
						count++;
						continue;
					}
					List<Planet> between = data.planets.planetsBetween(planet, agreement.planet);
					Ship ship = new Ship(agreement.resource, capacity, between, data);
					data.activeShips.add(ship);
					break;
				}
			}
			fireWaiting=!fireWaiting;
		}
	}
	
	private boolean hasTasks(Planet planet){
		return !planet.getActiveTrades().isEmpty()||!waitingShips.isEmpty();
	}

	@Override
	public String getName() {
		return "Space Port";
	}

}
