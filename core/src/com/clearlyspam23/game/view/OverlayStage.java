package com.clearlyspam23.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.clearlyspam23.game.model.Planet;

public class OverlayStage extends Stage {
	
	private Texture sideMenuBackground;
	private Texture buttonTexture;
	private NinePatch backgroundPatch;
	private NinePatch upButtonPatch;
	private NinePatch downButtonPatch;
	
	private BitmapFont font;
	
	private Skin skin;
	
	private SideOverlay overlay;
	
	public OverlayStage(){
		super(new ScreenViewport());
		font = new BitmapFont(Gdx.files.internal("fonts/GameMedium.fnt"));
		skin = new Skin();
		skin.add("default", new LabelStyle(font, Color.WHITE));
		sideMenuBackground = new Texture("art/MenuBackground1.png");
		backgroundPatch = new NinePatch(sideMenuBackground, 10, 32-27, 10, 32-27);
		buttonTexture = new Texture("art/SpaceButton.png");
		upButtonPatch = new NinePatch(new TextureRegion(buttonTexture, 0, 0, 32, 32), 8, 8, 8, 8);
		downButtonPatch = new NinePatch(new TextureRegion(buttonTexture, 32, 0, 32, 32), 8, 8, 8, 8);
		skin.add("default", new Button.ButtonStyle(new NinePatchDrawable(upButtonPatch), new NinePatchDrawable(downButtonPatch), new NinePatchDrawable(downButtonPatch)));
	}
	
	public void showPlanetOverlay(Planet planet){
		if(overlay instanceof PlanetOverlay && ((PlanetOverlay)overlay).getPlanet()==planet){
			System.out.println("not reshowing");
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
