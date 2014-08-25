package com.clearlyspam23.game.view.srenderes;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.structures.Mine;
import com.clearlyspam23.game.view.StructureRender;

public abstract class MineRender extends StructureRender {
	
	private TextureRegionDrawable structure;
	private TextureRegionDrawable base;
	
	protected Mine mine;
	protected Skin skin;

	public MineRender(Drawable background, float width, Map<Resource, TextureRegion> resourceMap, TextureRegion structureIcon, Mine mine, Skin skin) {
		super(background);
		structure = new TextureRegionDrawable(structureIcon);
		base = new TextureRegionDrawable(resourceMap.get(mine.getResource()));
		this.mine = mine;
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
		Image front = new Image();
		front.setDrawable(base);
		front.setBounds(0, 0, 32, 32);
		group.addActor(front);
		return group;
	}

	@Override
	protected Actor getDescription(float width) {
		Table table = new Table();
		table.columnDefaults(0).size(width, 32);
		Table descriptionTable = new Table();
		descriptionTable.setSize(width, 32);
		descriptionTable.add(new Label(mine.getName(), skin));
		Image image = new Image();
		image.setDrawable(base);
		descriptionTable.add(image);
		table.add(descriptionTable);
		table.row();
		table.add(getSubTitle(width));
		return table;
	}
	
	protected abstract Actor getSubTitle(float width);

}
