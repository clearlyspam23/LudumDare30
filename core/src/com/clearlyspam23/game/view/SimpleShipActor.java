package com.clearlyspam23.game.view;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Ship;

public class SimpleShipActor extends Image {
	
	private GameData data;
	private Ship ship;
	
	public SimpleShipActor(TextureRegion base, float startX, float startY, float endX, float endY, Ship ship, GameData data){
		setDrawable(new TextureRegionDrawable(base));
		this.setPosition(startX, startY);
		addAction(Actions.moveTo(endX, endY, ship.totalDistance*GameStage.TICK_RATE));
		this.data = data;
		this.ship = ship;
		setSize(10, 10);
	}
	
	public void act(float delta){
		super.act(delta);
		if(!data.getActiveShips().contains(ship))
			remove();
	}

}
