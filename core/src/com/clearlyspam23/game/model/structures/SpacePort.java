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
		super(0, 20000, 40000);
		this.rate = rate;
		this.capacity = capacity;
	}

	@Override
	public void performEffect(GameData data, Planet planet) {
		if(data.tick%(rate-getLevel())==0&&hasTasks(planet)){
			if(fireWaiting&&!waitingShips.isEmpty()){
				data.addShip(waitingShips.remove(0));
			}
			else{
				int count = 0;
				while(count<planet.getActiveTrades().size()){
					if(lastIndex>=planet.getActiveTrades().size())
						lastIndex = 0;
					TradeAgreement agreement = planet.getActiveTrades().get(lastIndex++);
					int amount = planet.removeResourceAmount(agreement.resource, capacity+25*getLevel());
					if(amount<=0){
						count++;
						continue;
					}
					List<Planet> between = data.getPlanetGrid().planetsBetween(planet, agreement.planet);
					Ship ship = new Ship(agreement.resource, amount, between, data, planet);
					data.addShip(ship);
					break;
				}
			}
			fireWaiting=!fireWaiting;
		}
	}
	
	private boolean hasTasks(Planet planet){
		return !planet.getActiveTrades().isEmpty()||!waitingShips.isEmpty();
	}
	
	public void addWaitingShip(Ship ship){
		waitingShips.add(ship);
	}

	@Override
	public String getName() {
		return "Space Port";
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public int getRate(){
		return rate;
	}

	@Override
	public Structure copy() {
		return new SpacePort(rate, capacity);
	}
	
	public boolean equals(Object o){
		return o instanceof SpacePort;
	}
	
	public int hashCode(){
		return 4;
	}

}
