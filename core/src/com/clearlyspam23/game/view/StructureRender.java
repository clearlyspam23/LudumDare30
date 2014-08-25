package com.clearlyspam23.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class StructureRender extends Button {
	
	public StructureRender(Drawable background){
		super(background);
	}
	
	public void init(float width){
		setSize(width, 64);
		add(getIcon()).size(64);
		add(getDescription(width-64)).size(width-64, 64);
	}
	
	public void setDisabled(boolean flag){
		super.setDisabled(flag);
		if(!flag)
			setColor(Color.WHITE);
		else
			setColor(Color.GRAY);
	}
	
	protected abstract Actor getIcon();
	
	protected abstract Actor getDescription(float width);

}
