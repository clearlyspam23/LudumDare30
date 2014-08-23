package com.clearlyspam23.game.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainView {
	
	private OrthographicCamera camera;
	
	private List<Sprite> planets = new ArrayList<Sprite>();
	private List<ShipSprite> ships = new ArrayList<ShipSprite>();
	
	public MainView(float width, float height){
		camera = new OrthographicCamera(width, height);
	}

	public List<Sprite> getPlanets() {
		return planets;
	}

	public void setPlanets(List<Sprite> planets) {
		this.planets = planets;
	}

	public List<ShipSprite> getShips() {
		return ships;
	}

	public void setShips(List<ShipSprite> ships) {
		this.ships = ships;
	}

}
