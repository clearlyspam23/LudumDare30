package com.clearlyspam23.game.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.GameEventListener;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Ship;

public class UniverseStage extends Stage implements GameEventListener{
	
	public static float TICK_RATE = 0.25f;
	
	private final GameData data;
	
	private float sinceLastTick;
	
	private Map<Planet, Actor> actorMap = new HashMap<Planet, Actor>();
	
	private Group planetGroup = new Group();
	private Group shipGroup = new Group();
	
	private Texture ship;
	private TextureRegion shipBase;
	private TextureRegion shipOverlay;
	
//	private Texture simpleShip;
//	private TextureRegion simpleShipRegion;
	
	private Texture planet;
	private TextureRegion planetRegion;
	
	private Vector2 mouseDown = new Vector2();
	
	private Vector2 maximumScale;
	private Vector2 minimumScale;
	
	private float scaleRate = 20;

	public UniverseStage(Viewport viewport, GameData data) {
		super(viewport);
		System.out.println(getCamera().viewportWidth + ", " + getCamera().viewportHeight);
		maximumScale = new Vector2(getCamera().viewportWidth*2, getCamera().viewportHeight*2);
		minimumScale = new Vector2(getCamera().viewportWidth*0.5f, getCamera().viewportHeight*0.5f);
		this.data = data;
		data.addListener(this);
		addActor(planetGroup);
		addActor(shipGroup);
		
		ship = new Texture("art/SpaceShip.png");
		shipBase = new TextureRegion(ship, 0, 0, 32, 64);
		shipOverlay = new TextureRegion(ship, 32, 0, 32, 64);
		
		planet = new Texture("art/SadPlanet.png");
		planetRegion = new TextureRegion(planet);
		
//		simpleShip = new Texture("art/SpaceShipCombined.png");
//		simpleShipRegion = new TextureRegion(simpleShip);
//		viewport.getCamera().position.set(-50, -50, 0);
	}
	
	@Override
	public void act(float delta){
		sinceLastTick+=delta;
		while(sinceLastTick>=TICK_RATE){
			data.doTick();
			sinceLastTick-=TICK_RATE;
		}
		super.act(delta);
	}

	@Override
	public void onShipAdd(Ship ship, GameData data) {
		Actor startActor = actorMap.get(ship.getCurrentPlanet());
//		System.out.println(startActor.getX());
//		System.out.println(startActor.getY());
		Actor endActor = actorMap.get(ship.getNextVisit());
		ShipActor shipActor = new ShipActor(shipBase, shipOverlay, startActor.getOriginX(), startActor.getOriginY(), 
				endActor.getOriginX(), endActor.getOriginY(), ship, data);
		shipGroup.addActor(shipActor);
	}

	@Override
	public void onMoneyChange(int amount, Planet sourcePlanet, GameData data) {
		
	}
	
	public void addPlanet(Planet planet, Color color, float x, float y, float size){
		Image image = new Image();
		image.setOrigin(x+size/2, y+size/2);
		image.setX(x);
		image.setY(y);
		image.setDrawable(new TextureRegionDrawable(planetRegion));
		image.setColor(color);
		image.setSize(size, size);
		planetGroup.addActor(image);
		actorMap.put(planet, image);
	}
	
	public void dispose(){
		super.dispose();
		ship.dispose();
		planet.dispose();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		if(button==Buttons.LEFT&&pointer==0){
			mouseDown.set(screenX, screenY);
			mouseDown = this.screenToStageCoordinates(mouseDown);
		}
		return super.touchDown(screenX, screenY, pointer, button);
	}
	
	public boolean touchDragged(int screenX, int screenY, int pointer){
		Vector2 current = new Vector2(screenX, screenY);
		Vector2 screen = this.screenToStageCoordinates(current);
		screen.sub(mouseDown);
		this.getCamera().position.sub(screen.x, screen.y, 0);
		return true;
	}
	
	@Override
	public boolean scrolled(int amount){
		getCamera().viewportWidth = Math.max(minimumScale.x, Math.min(maximumScale.x, getCamera().viewportWidth+scaleRate*amount));
		getCamera().viewportHeight = Math.max(minimumScale.y, Math.min(maximumScale.y, getCamera().viewportHeight+scaleRate*amount));
		return true;
	}

//	@Override
//	public void onPlanetAdd(Planet planet, GameData data) {
//		// TODO Auto-generated method stub
//		
//	}

}
