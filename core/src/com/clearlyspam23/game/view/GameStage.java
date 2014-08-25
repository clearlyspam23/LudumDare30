package com.clearlyspam23.game.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.clearlyspam23.game.LD30SpaceGame;
import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.GameEventListener;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Ship;

public class GameStage extends Stage implements GameEventListener{
	
	public static float TICK_RATE = 0.25f;
	
	private final GameData data;
	
	private float sinceLastTick;
	
	private Map<Planet, Actor> actorMap = new HashMap<Planet, Actor>();
	
	private Group planetGroup = new Group();
	private Group shipGroup = new Group();
	
	private Texture ship;
	private TextureRegion shipBase;
	private TextureRegion shipOverlay;
	
	private ParticleEffectPool flameEffectPool;
	private ParticleEffect flameEffect = new ParticleEffect();
	
	private boolean didDrag = false;
	private boolean dragging = false;
	
	private OverlayStage overlay;
	private StarsStage stars;
	
	private Texture planet;
	private TextureRegion planetRegion;
	
	private Vector2 mouseDown = new Vector2();
	
	private Vector2 maximumScale;
	private Vector2 minimumScale;
	
	private float scaleRate = 30;

	public GameStage(Viewport viewport, GameData data, LD30SpaceGame ld30SpaceGame) {
		super(viewport);
		overlay = new OverlayStage(ld30SpaceGame);
		stars = new StarsStage(new ScreenViewport(), ld30SpaceGame.starsDrawable);
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
		
		flameEffect.load(Gdx.files.internal("particles/SpaceShipFlame.prt"), Gdx.files.internal("art"));
		flameEffectPool = new ParticleEffectPool(flameEffect, 50, 100);
//		for(int i = 0; i < 25; i++){
//			ParticleEffectActor effect = new ParticleEffectActor(flameEffectPool.obtain());
//			effect.setPosition(50*(i%5), 50*(i/5));
//			addActor(effect);
//		}
//		addActor(new ParticleEffectActor(flameEffectPool.obtain()));
		
		
//		viewport.getCamera().position.set(-50, -50, 0);
	}
	
	@Override
	public void act(float delta){
		sinceLastTick+=delta;
		while(sinceLastTick>=TICK_RATE){
			data.doTick();
			sinceLastTick-=TICK_RATE;
		}
		stars.act();
		overlay.act(delta);
		super.act(delta);
	}
	
	@Override
	public void draw(){
		stars.draw();
		super.draw();
		overlay.draw();
	}

	@Override
	public void onShipAdd(Ship ship, GameData data) {
		Actor startActor = actorMap.get(ship.getCurrentPlanet());
//		System.out.println(startActor.getX());
//		System.out.println(startActor.getY());
		Actor endActor = actorMap.get(ship.getNextVisit());
		Vector2 towards = new Vector2(endActor.getOriginX(), endActor.getOriginY());
		towards.sub(startActor.getOriginX(), startActor.getOriginY()).nor();
		Vector2 otherTowards = new Vector2(towards);
		towards.scl(startActor.getWidth()*0.6f);
		otherTowards.scl(endActor.getWidth()*0.6f);
		ShipActor shipActor = new ShipActor(shipBase, shipOverlay, flameEffectPool.obtain(), startActor.getOriginX()+towards.x, startActor.getOriginY()+towards.y, 
				endActor.getOriginX()-otherTowards.x, endActor.getOriginY()-otherTowards.y, ship, data);
		shipGroup.addActor(shipActor);
	}

	@Override
	public void onMoneyChange(int amount, Planet sourcePlanet, GameData data) {
		
	}
	
	public void addPlanet(final Planet planet, Color color, float x, float y, float size){
		Image image = new Image();
		image.setX(x);
		image.setY(y);
		image.setOrigin(x+size/2, y+size/2);
		image.setDrawable(new TextureRegionDrawable(planetRegion));
		image.setColor(color);
		image.setSize(size, size);
		image.addListener(new ClickListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				overlay.showPlanetOverlay(planet);
				return true;
			}
			
		});
		planetGroup.addActor(image);
		actorMap.put(planet, image);
	}
	
	public void dispose(){
		super.dispose();
		overlay.dispose();
		ship.dispose();
		planet.dispose();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		if(overlay.touchDown(screenX, screenY, pointer, button)){
			didDrag = true;
			return true;
		}
		
		
		if(!super.touchDown(screenX, screenY, pointer, button)){
			if(button==Buttons.LEFT&&pointer==0){
				mouseDown.set(screenX, screenY);
				mouseDown = this.screenToStageCoordinates(mouseDown);
				dragging = true;
			}
		}
		else{
			didDrag = true;
		}
		return true;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button){
		overlay.touchUp(screenX, screenY, pointer, button);
		if(!didDrag)
			overlay.hideActiveOverlay();
		didDrag = false;
		dragging = false;
		return true;
	}
	
	public boolean touchDragged(int screenX, int screenY, int pointer){
		if(dragging){
			didDrag = true;
			Vector2 current = new Vector2(screenX, screenY);
			Vector2 screen = this.screenToStageCoordinates(current);
			screen.sub(mouseDown);
			this.getCamera().position.sub(screen.x, screen.y, 0);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean scrolled(int amount){
		float aspectRatio = getCamera().viewportWidth/getCamera().viewportHeight;
		float nWidth = getCamera().viewportWidth+scaleRate*aspectRatio*amount;
		if(nWidth<minimumScale.x||nWidth>=maximumScale.x)
			return true;
		float nHeight = getCamera().viewportHeight+scaleRate*amount;
		if(nHeight<minimumScale.y||nHeight>=maximumScale.y)
			return true;
		getCamera().viewportWidth = nWidth;
		getCamera().viewportHeight = nHeight;
		return true;
	}
	
	public OverlayStage getOverlay(){
		return overlay;
	}

//	@Override
//	public void onPlanetAdd(Planet planet, GameData data) {
//		// TODO Auto-generated method stub
//		
//	}

}
