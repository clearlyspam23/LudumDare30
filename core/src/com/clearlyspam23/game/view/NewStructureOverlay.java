package com.clearlyspam23.game.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Structure;

public class NewStructureOverlay extends ModularOverlay {
	
	private Map<Button, Structure> structuresMap = new HashMap<Button, Structure>();
	private PlanetOverlay pOverlay;

	public NewStructureOverlay(OverlayStage stage, NinePatch background, Planet source, final PlanetOverlay pOverlay) {
		super(stage, background);
		this.pOverlay = pOverlay;
		this.setSize(600, 600);
		Table table = new Table();
		table.align(Align.top);
		table.setBackground(new NinePatchDrawable(stage.greyBackground));
		table.setSize(594, 600-40-4);
		for(Resource resource : source.getAvailableResources()){
			if(!stage.allowanceMap.containsKey(resource))
				continue;
			Structure s = stage.allowanceMap.get(resource).copy();
			boolean flag = false;
			for(Structure str : source.getStructures()){
				if(str.getName().equals(s.getName())){
					flag = true;
				}
			}
			if(flag)
				continue;
			System.out.println(s.getClass());
			final StructureRender button = stage.activeStructureMap.get(s.getClass()).getRenderer(s, 594, pOverlay);
			button.addListener(new ClickListener(){
				public void clicked(InputEvent event, float screenX, float screenY){
					if(button.isChecked()){
						for(Button b : structuresMap.keySet())
							if(!b.equals(button))
								b.setChecked(false);
					}
				}
			});
			button.setDisabled(!pOverlay.canMakePurchase(s.getBuyingValue()));
			structuresMap.put(button, s);
			table.add(button);
			table.row();
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
				for(Entry<Button, Structure> e : structuresMap.entrySet()){
					if(e.getKey().isChecked()){
						pOverlay.addStructure(e.getValue());
						break;
					}
				}
				hide();
			}
		});
		Color c = Color.WHITE;
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
	
	public void act(float delta){
		for(Entry<Button, Structure> e : structuresMap.entrySet()){
			e.getKey().setDisabled(!pOverlay.canMakePurchase(e.getValue().getBuyingValue()));
		}
	}

}
