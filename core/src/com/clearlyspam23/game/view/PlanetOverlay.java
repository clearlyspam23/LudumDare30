package com.clearlyspam23.game.view;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Structure;
import com.clearlyspam23.game.model.TradeAgreement;

public class PlanetOverlay extends SideOverlay implements PurchaseMaker{
	
	private Planet planet;
	private Table table;
	private OverlayStage overlay;
	private Button addTradeButton;
	private Button removeTradeButton;
	
	private Label ownedStatus;
	
	private Table structureTable;
	
	private Skin skin;
	
	private final float width = 300;
	private final float height = 800;
	
	private final NinePatch patch;
	
	private Table resourceTable;
	
	public PlanetOverlay(final NinePatch patch, final Planet planet, final Skin skin, final OverlayStage overlay){
		super(patch);
		this.planet = planet;
		this.patch = patch;
		table = new Table(skin);
		this.skin = skin;
		this.overlay = overlay;
		setSize(width, height);
		align(Align.top);
		this.pad(2);
		
		addTradeButton = new Button(new NinePatchDrawable(overlay.upButtonPatch), new NinePatchDrawable(overlay.downButtonPatch));
		formatButton(addTradeButton, overlay.accept, "New Trade", skin, width).setPosition(3, height-40);
		add(addTradeButton).size(addTradeButton.getWidth(), addTradeButton.getHeight());
		addTradeButton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float screenX, float screenY){
				NewTradeOverlay trade = new NewTradeOverlay(overlay, patch, planet, PlanetOverlay.this);
				trade.show();
			}
		});
		row();
		removeTradeButton = new Button(skin);
		formatButton(removeTradeButton, overlay.decline, "Cancel Trade", skin, width).setPosition(3, height-80);
		add(removeTradeButton).size(removeTradeButton.getWidth(), removeTradeButton.getHeight());
		removeTradeButton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float screenX, float screenY){
				CancelTradeOverlay trade = new CancelTradeOverlay(overlay, patch, planet, PlanetOverlay.this);
				trade.show();
			}
		});
		row();
		
		table.setSize(width-6, 50);
		Label label = new Label("name : ", skin);
		label.setAlignment(Align.left);
		table.add(label).size(80, 25);
//		table.add("name: ");
		label = new Label(planet.getName(), skin);
		label.setAlignment(Align.left);
		table.add(label).size(width-6-80, 25);
		table.row();
		label = new Label("status: ", skin);
		label.setAlignment(Align.left);
		table.add(label).size(80, 25);
		ownedStatus = new Label("owned", skin);
		ownedStatus.setAlignment(Align.left);
		table.add(ownedStatus).size(width-6-80, 25);
		table.row();
//		table.setBounds(0, 100, 200, 100);
		add(table).size(width, 50);
		row();
		structureTable = new Table();
		reloadStructureTable();
		add(structureTable);
		row();
		resourceTable = new Table();
		reloadResourceTable();
		add(resourceTable);
	}
	
	private void reloadResourceTable(){
		resourceTable.clear();
		resourceTable.setSize(width-6, 32*16);
		resourceTable.columnDefaults(0).size(32);
		resourceTable.columnDefaults(1).size((width-6)/2-32, 32);
		resourceTable.columnDefaults(2).size(32);
		resourceTable.columnDefaults(1).size((width-6)/2-32, 32);
		int i = 0;
		for(Resource r : planet.getAvailableResources()){
			resourceTable.add(new Image(overlay.resourceMap.get(r)));
			Label label = new Label("" + planet.getResourceAmount(r), skin, "small");
			label.setAlignment(Align.left);
			resourceTable.add(label);
			if((++i)%2==0)
				resourceTable.row();
		}
	}
	
	private void reloadStructureTable(){
		structureTable.clear();
		structureTable.setSize(width-6, 64*5);
		structureTable.columnDefaults(0).size(width-6, 64);
		List<Structure> structures = planet.getStructures();
		for(int i = 0; i < 5; i++){
			if(i<structures.size()){
				StructureRenderFactory fact = overlay.passiveStructureMap.get(structures.get(i).getClass());
				StructureRender render = fact.getRenderer(structures.get(i), width-6, this);
				structureTable.add(render);
			}
			else if(i==structures.size()){
				Button button = new Button(skin);
				button.padLeft(4);
				button.columnDefaults(0).align(Align.right).width(60);
				button.columnDefaults(1).width(width-6-64-8).center();
//				button.setDisabled(true);
				Image image = new Image();
				image.setDrawable(new TextureRegionDrawable(overlay.accept));
				image.setSize(64, 64);
				button.add(image).size(60);
				Label label = new Label("New Structure", skin);
				label.setAlignment(Align.center);
				button.add(label);
				button.setSize(width-6, 40);
				structureTable.add(button).size(width-6, 64);
				button.addListener(new ClickListener(){
					public void clicked(InputEvent event, float x, float y){
						NewStructureOverlay structure = new NewStructureOverlay(overlay, patch, planet, PlanetOverlay.this);
						structure.show();
					}
				});
			}
			else{
				Image blank = new Image();
				blank.setDrawable(new NinePatchDrawable(overlay.greyBackground));
				structureTable.add(blank);
			}
			structureTable.row();
		}
	}
	
	public void act(float delta){
		super.act(delta);
		reloadResourceTable();
	}

	@Override
	public void onUpdate() {
		
	}
	
	public void draw(Batch batch, float whocares){
		super.draw(batch, whocares);
	}

	public Planet getPlanet() {
		return planet;
	}
	
	private <T extends Button> T formatButton(final T button, TextureRegion icon, String text, Skin skin, float width){
		button.padLeft(4);
		button.columnDefaults(0).align(Align.left).width(32);
		button.columnDefaults(1).width(width-6-32-8).center();
//		button.setDisabled(true);
		Image image = new Image();
		image.setDrawable(new TextureRegionDrawable(icon));
		image.setSize(32, 32);
		button.add(image).size(32);
		Label label = new Label(text, skin);
		label.setAlignment(Align.center);
		button.add(label);
		button.setSize(width-6, 40);
//		button.addListener(new ClickListener(){
//
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				
//			}
//			
//		});
		return button;
	}
	
	public void addTradeAgreements(List<PotentialTrade> trades){
		for(PotentialTrade t : trades){
			t.source.addTradeAgreement(new TradeAgreement(t.resource, t.destination));
		}
	}
	
	public void cancelTrades(List<TradeAgreement> trades){
		for(TradeAgreement a : trades){
			planet.removeTradeAgreement(a);
		}
	}
	
	public void addStructure(Structure structure){
		makePurchase(structure.getBuyingValue());
		planet.addStructure(structure);
		reloadStructureTable();
	}

	@Override
	public boolean canMakePurchase(int amount) {
		return overlay.data.getAmountOfMoney()>=amount;
	}

	@Override
	public void makePurchase(int amount) {
		overlay.data.changeAmountOfMoney(-amount, planet);
	}

}
