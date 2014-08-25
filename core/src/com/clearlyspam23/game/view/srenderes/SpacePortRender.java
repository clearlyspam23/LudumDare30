package com.clearlyspam23.game.view.srenderes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.clearlyspam23.game.model.structures.SpacePort;
import com.clearlyspam23.game.view.StructureRender;

public abstract class SpacePortRender extends StructureRender {
	
	private TextureRegionDrawable structure;
//	private TextureRegionDrawable result;
//	private TextureRegionDrawable base;
	
	protected SpacePort port;
	protected Skin skin;

	public SpacePortRender(Drawable background, float width, TextureRegion structureIcon, SpacePort port, Skin skin) {
		super(background);
		structure = new TextureRegionDrawable(structureIcon);
//		result = new TextureRegionDrawable(resourceMap.get(port.getResult()));
//		base = new TextureRegionDrawable(resourceMap.get(refinery.getBase()));
		this.port = port;
		this.skin = skin;
	}

	@Override
	protected Actor getIcon() {
		Group group = new Group();
		group.setSize(64, 64);
		Image back = new Image();
		back.setDrawable(structure);
		back.setSize(64, 64);
		group.addActor(back);
//		Image front = new Image();
//		front.setDrawable(result);
//		front.setBounds(0, 32, 32, 32);
//		group.addActor(front);
		return group;
	}

	@Override
	protected Actor getDescription(float width) {
		Table table = new Table();
		table.columnDefaults(0).size(width, 32);
		table.add(new Label("Allows transportation of goods", skin, "small"));
//		Table descriptionTable = new Table();
//		descriptionTable.setSize(width, 32);
//		Image image = new Image();
//		image.setDrawable(base);
//		image.setSize(32, 32);
//		descriptionTable.add(image);
//		descriptionTable.add(new Label("->", skin));
//		image = new Image();
//		image.setDrawable(result);
//		descriptionTable.add(image);
//		table.add(descriptionTable);
		table.row();
		table.add(getSubTitle(width));
		return table;
	}
	
	protected abstract Actor getSubTitle(float width);

}
