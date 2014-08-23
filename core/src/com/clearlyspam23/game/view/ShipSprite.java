package com.clearlyspam23.game.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShipSprite {
	
	private Sprite base;
	private Sprite overlay;
	
	public ShipSprite(TextureRegion base, TextureRegion overlay){
		this.base = new Sprite(base);
		this.base.setOriginCenter();
		this.overlay = new Sprite(overlay);
		this.overlay.setOriginCenter();
	}
	
	public void setPosition(float x, float y){
		base.setPosition(x, y);
		overlay.setPosition(x, y);
	}
	
	public void setSize(float width, float height){
		base.setSize(width, height);
		overlay.setSize(width, height);
	}
	
	public void setRotation(float rotation){
		base.setRotation(rotation);
		overlay.setRotation(rotation);
	}

}
