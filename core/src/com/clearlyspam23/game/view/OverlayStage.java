package com.clearlyspam23.game.view;

import java.util.Map;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.clearlyspam23.game.LD30SpaceGame;
import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Structure;

public class OverlayStage extends Stage {
	
	
	public final NinePatch backgroundPatch;
	
	public final NinePatch upButtonPatch;
	public final NinePatch downButtonPatch;
	
	public final NinePatch greyBackground;
	
	public final NinePatch smallButtonPatch;
	
	public final TextureRegion accept;
	public final TextureRegion decline;
	
	public final TextureRegion acceptOverlay;
	public final TextureRegion declineOverlay;
	
//	public final BitmapFont font;
//	public final BitmapFont small;
	
	public final Skin skin;
	
	private SideOverlay overlay;
	private ModularOverlay modularDisplay;
	private Table infoTable;
	
	public final Map<Resource, TextureRegion> resourceMap;
	public final Map<Class<? extends Structure>, StructureRenderFactory> passiveStructureMap;
	public final Map<Class<? extends Structure>, StructureRenderFactory> activeStructureMap;
	public final Map<Resource, Structure> allowanceMap;
	
	public final GameData data;
	
	private Label moneyLabel;
	private Label shipsLabel;
	
	public OverlayStage(LD30SpaceGame game){
		super(new ScreenViewport());
		data = game.data;
		passiveStructureMap = game.passiveStructureMap;
		activeStructureMap = game.activeStructureMap;
		allowanceMap = game.allowanceMap;
		resourceMap = game.resourceIcons;
		backgroundPatch = game.backgroundPatch;
		upButtonPatch = game.upButtonPatch;
		downButtonPatch = game.downButtonPatch;
		greyBackground = game.greyBackground;
		smallButtonPatch = game.smallButtonPatch;
		accept = game.accept;
		decline = game.decline;
		acceptOverlay = game.acceptOverlay;
		declineOverlay = game.declineOverlay;
		skin = game.skin;
		infoTable = new Table();
		infoTable.setBackground(new NinePatchDrawable(game.greyBackground));
		infoTable.columnDefaults(0).size(64);
		infoTable.columnDefaults(1).size(200-64, 64);
		Image image = new Image();
		image.setDrawable(new TextureRegionDrawable(game.money));
		infoTable.add(image);
		moneyLabel = new Label(""+game.data.getAmountOfMoney(), skin);
		moneyLabel.setAlignment(Align.left);
		infoTable.add(moneyLabel);
		infoTable.row();
		image = new Image();
		image.setDrawable(new TextureRegionDrawable(game.simpleShipRegion));
		infoTable.add(image);
		shipsLabel = new Label(""+game.data.getActiveShips().size(), skin);
		shipsLabel.setAlignment(Align.left);
		infoTable.add(shipsLabel);
		infoTable.setSize(200, 134);
		infoTable.setTouchable(Touchable.disabled);
		addActor(infoTable);
	}
	
	public void act(float delta){
		super.act(delta);
		moneyLabel.setText(""+data.getAmountOfMoney());
		shipsLabel.setText(""+data.getActiveShips().size());
	}
	
	public void draw(){
		super.draw();
		Button.drawDebug(this);
	}
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		boolean b = super.touchDown(screenX, screenY, pointer, button);
		if(modularDisplay!=null){
			return true;
		}
		if(pointer!=0||button!=Buttons.LEFT)
			return b;
		Vector2 location = this.screenToStageCoordinates(new Vector2(screenX, screenY));
		b = hit(location.x, location.y, false)!=null;
		return b;
	}
	
	public void setModularDisplay(ModularOverlay overlay){
		modularDisplay = overlay;
		if(modularDisplay==null){
			if(this.overlay!=null)
				this.overlay.setTouchable(Touchable.enabled);
		}
		else{
			if(this.overlay!=null)
				this.overlay.setTouchable(Touchable.disabled);
		}
	}
	
	public void showPlanetOverlay(Planet planet){
		if(overlay instanceof PlanetOverlay && ((PlanetOverlay)overlay).getPlanet()==planet){
			return;
		}
		hideActiveOverlay();
		overlay = new PlanetOverlay(backgroundPatch, planet, skin, this);
		addActor(overlay);
		overlay.show();
	}
	
	public void hideActiveOverlay(){
		if(overlay!=null){
			overlay.hide();
			overlay = null;
		}
	}

}
