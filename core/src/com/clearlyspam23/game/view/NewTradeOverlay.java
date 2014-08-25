package com.clearlyspam23.game.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;

public class NewTradeOverlay extends ModularOverlay {
	
	private Map<Button, PotentialTrade> tradesMap = new HashMap<Button, PotentialTrade>();

	public NewTradeOverlay(OverlayStage stage, NinePatch background, Planet source, final PlanetOverlay pOverlay) {
		super(stage, background);
		this.setSize(600, 600);
		Table table = new Table();
		table.align(Align.top);
		table.setBackground(new NinePatchDrawable(stage.greyBackground));
		table.setSize(594, 600-40-4);
		for(Planet planet : source.getNeighboringPlanets()){
			for(Resource resource : planet.getNonTradedResource(source)){
				PotentialTrade t = new PotentialTrade(planet, source, resource);
				final Button button = new Button(stage.skin);
				tradesMap.put(button, t);
				button.columnDefaults(0).size(64);
				button.columnDefaults(1).size(600-6-64, 64);
				Group overlay = new Group();
				overlay.setSize(64, 64);
				Image image = new Image(new TextureRegionDrawable(stage.resourceMap.get(resource)));
				image.setSize(64, 64);
				overlay.addActor(image);
				final Image acceptOverlay = new Image(new TextureRegionDrawable(stage.acceptOverlay));
				acceptOverlay.setSize(64, 64);
				acceptOverlay.setVisible(false);
				overlay.addActor(acceptOverlay);
				button.add(overlay);
				button.addListener(new ClickListener(){
					public void clicked(InputEvent event, float screenX, float screenY){
						acceptOverlay.setVisible(button.isChecked());
					}
				});
				Table textTable = new Table();
				float calculatedValue = source.getResourceBuyingValue(resource)*planet.getSpacePort().getCapacity()/((planet.getSpacePort().getRate() + source.distanceToPlanet(planet))*GameStage.TICK_RATE);
				Label label = new Label((calculatedValue > 0 ? "Trade " : "Move ") + resource.name + " from " + planet.getName(), stage.skin);
				label.setAlignment(Align.center);
				textTable.add(label);
				textTable.row();
				label = new Label("estimated VPS: " + poorMansFormat(calculatedValue), stage.skin, "small");
				label.setAlignment(Align.center);
				textTable.add(label);
				button.add(textTable);
				table.add(button);
				table.row();
			}
		}
		ScrollPane pane = new ScrollPane(table);
		pane.setSize(594, 600-40-4);
		add(pane).size(594, 600-40-4);
		row();
		Table buttonTable = new Table();
		buttonTable.setSize(594, 532);
		buttonTable.align(Align.right);
		Button button = new Button(new NinePatchDrawable(stage.smallButtonPatch));
		button.setSize(120, 40);
		Label label = new Label("Accept" , stage.skin, "small");
		label.setAlignment(Align.center);
		button.add(label);
		button.addListener(new ClickListener(){
			public void clicked(InputEvent event, float screenX, float screenY){
				List<PotentialTrade> trades = new ArrayList<PotentialTrade>();
				for(Entry<Button, PotentialTrade> e : tradesMap.entrySet()){
					if(e.getKey().isChecked())
						trades.add(e.getValue());
				}
				pOverlay.addTradeAgreements(trades);
				hide();
			}
		});
		buttonTable.add(button);
		button = new Button(new NinePatchDrawable(stage.smallButtonPatch));
		button.setSize(120, 40);
		label = new Label("Decline" , stage.skin, "small");
		label.setAlignment(Align.center);
		button.add(label);
		button.addListener(new ClickListener(){
			public void clicked(InputEvent event, float screenX, float screenY){
				hide();
			}
		});
		buttonTable.add(button);
		add(buttonTable);
	}
	
	private String poorMansFormat(float f){
		String out = Float.toString(f);
		int indexOf = out.indexOf('.');
		if(indexOf>=0&&out.length()>indexOf+2){
			out = out.substring(0, indexOf+2);
		}
		return out;
	}

}
