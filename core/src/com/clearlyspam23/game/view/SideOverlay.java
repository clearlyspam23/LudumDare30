package com.clearlyspam23.game.view;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public abstract class SideOverlay extends Group {
	
	private boolean showing;
	
	private Image background;
	
	public SideOverlay(NinePatch ninePatch){
		background = new Image();
		background.setDrawable(new NinePatchDrawable(ninePatch));
		addActor(background);
	}
	
	@Override
	public void setSize(float width, float height){
		super.setSize(width, height);
		background.setSize(width, height);
	}
	
	protected void setStage(Stage stage){
		super.setStage(stage);
		if(stage!=null)
			this.setPosition(stage.getCamera().viewportWidth, 0);
	}
	
	public void show(){
		float width = this.getStage().getCamera().viewportWidth;
		this.addAction(Actions.moveTo(width-this.getWidth(), 0, 0.1f));
		showing = true;
	}
	
	public void hide(){
//		if(this.getStage()==null||getStage().getCamera()==null){
//			return;
//		}
		float width = this.getStage().getCamera().viewportWidth;
		this.addAction(Actions.sequence(Actions.moveTo(width, 0, 0.1f), Actions.removeActor()));
		showing = false;
	}
	
	public void onResize(float nWidth, float nHeight){
		
	}
	
	public boolean isShowing(){
		return showing;
	}
	
	public abstract void onUpdate();

	public Image getBackground() {
		return background;
	}

}
