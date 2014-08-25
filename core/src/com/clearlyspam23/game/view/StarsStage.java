package com.clearlyspam23.game.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StarsStage extends Stage {
	
	private Image[][] images;
	
	public StarsStage(Viewport viewport, Drawable tile){
		super(viewport);
		images = new Image[(int) (viewport.getCamera().viewportWidth/256+2)][(int) (viewport.getCamera().viewportHeight/256+2)];
		for(int i = 0; i < images.length; i++){
			for(int j = 0; j < images[i].length; j++){
				images[i][j] = new Image(tile);
				images[i][j].setSize(256, 256);
				images[i][j].setPosition(-256+i*256, -256+j*256);
				addActor(images[i][j]);
			}
		}
	}

}
