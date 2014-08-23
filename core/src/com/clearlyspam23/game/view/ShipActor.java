package com.clearlyspam23.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Ship;

public class ShipActor extends Actor {
	
	private TextureRegion base;
	private TextureRegion overlay;
	private Ship ship;
	private GameData data;
	
	public ShipActor(TextureRegion base, TextureRegion overlay, float startX, float startY, float endX, float endY, Ship ship, GameData data){
		this.base = base;
		this.overlay = overlay;
		this.ship = ship;
		this.setPosition(startX, startY);
		addAction(Actions.moveTo(endX, endY, ship.totalDistance*UniverseStage.TICK_RATE));
		this.data = data;
		setSize(10, 20);
		float deltaX = endX - startX;
		float deltaY = endY - startY;
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX))-90;
		this.setRotation((float) angle);
	}
	
	public void act(float delta){
		super.act(delta);
		if(!data.getActiveShips().contains(ship))
			remove();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
//		super.draw(batch, parentAlpha);
		batch.setColor(getColor());
		batch.draw(base, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, getRotation());
		batch.setColor(Color.WHITE);
		batch.draw(overlay, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, getRotation());
	}
	
//	public boolean shouldRemove(GameData data){
//		return !data.activeShips.contains(ship);
//	}
	
//	public void setPosition(float x, float y){
//		base.setPosition(x, y);
//		overlay.setPosition(x, y);
//	}
//	
//	public void setSize(float width, float height){
//		base.setSize(width, height);
//		overlay.setSize(width, height);
//	}
//	
//	public void setRotation(float rotation){
//		base.setRotation(rotation);
//		overlay.setRotation(rotation);
//	}

}
