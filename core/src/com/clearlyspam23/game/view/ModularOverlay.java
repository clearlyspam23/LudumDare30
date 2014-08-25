package com.clearlyspam23.game.view;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class ModularOverlay extends Table {
	
	public final OverlayStage overlay;
	
	private Image background;
	
	public ModularOverlay(OverlayStage stage, NinePatch ninePatch){
		overlay = stage;
		background = new Image();
		background.setDrawable(new NinePatchDrawable(ninePatch));
		addActor(background);
	}
	
	public void setSize(float width, float height){
		super.setSize(width, height);
		this.setPosition(overlay.getCamera().viewportWidth/2-getWidth()/2, overlay.getCamera().viewportHeight/2-getHeight()/2);
		background.setSize(width, height);
	}
	
	public void show(){
		overlay.addActor(this);
		overlay.setModularDisplay(this);
	}
	
	public void hide(){
		overlay.setModularDisplay(null);
		this.remove();
	}

}
