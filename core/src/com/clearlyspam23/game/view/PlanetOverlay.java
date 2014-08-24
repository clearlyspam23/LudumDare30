package com.clearlyspam23.game.view;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.clearlyspam23.game.model.Planet;

public class PlanetOverlay extends SideOverlay {
	
	private Planet planet;
	private Table table;
	private OverlayStage overlay;
	
	public PlanetOverlay(NinePatch patch, Planet planet, Skin skin, OverlayStage overlay){
		super(patch);
		this.planet = planet;
		table = new Table(skin);
		this.overlay = overlay;
		setSize(200, 400);
		Button addTradeButton = new Button(skin);
		addTradeButton.add("New Trade");
//		addActor(addTradeButton);
		table.add("name: ");
		table.add(planet.getName());
		table.row();
		table.add("status: ");
		table.add("owned");
		table.row();
		table.setBounds(0, 100, 200, 100);
		addActor(table);
	}

	@Override
	public void onUpdate() {
		
	}

	public Planet getPlanet() {
		return planet;
	}

}
