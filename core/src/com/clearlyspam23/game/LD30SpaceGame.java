package com.clearlyspam23.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.TradeAgreement;
import com.clearlyspam23.game.model.ValueTable;
import com.clearlyspam23.game.model.Resource.ResourceType;
import com.clearlyspam23.game.model.logging.ConsoleLogger;
import com.clearlyspam23.game.model.structures.Mine;
import com.clearlyspam23.game.model.structures.Refinery;
import com.clearlyspam23.game.model.structures.SpacePort;
import com.clearlyspam23.game.view.GameStage;

public class LD30SpaceGame extends ApplicationAdapter {
	
	GameStage stage;
//	SpriteBatch batch;
	
	
	@Override
	public void create () {
		GameData data = new GameData();
		data.logger = new ConsoleLogger();
		
		Resource iron = new Resource("Iron", ResourceType.unrefined);
		Resource steel = new Resource("Steel", ResourceType.refined);
		ValueTable baseTable = new ValueTable();
		baseTable.addResource(iron, 100);
		baseTable.addResource(steel, 150);
		
		Planet planet1 = new Planet("Earth", baseTable, 1.0f, 1.0f, new SpacePort(5, 25));
		planet1.addStructure(new Mine(iron, 10, 50));
		data.addPlanet(planet1);
		Planet planet2 = new Planet("Mars", baseTable, 0.8f, 1.2f, new SpacePort(15, 50));
		planet2.addStructure(new Refinery(iron, steel, 20, 10));
		data.addPlanet(planet2);
		data.getPlanetGrid().makeThatConnection(planet1, planet2, 20);
		Planet planet3 = new Planet("Jupiter", baseTable, 1.2f, 0.8f, new SpacePort(5, 25));
		data.addPlanet(planet3);
		data.getPlanetGrid().makeThatConnection(planet2, planet3, 50);
		planet1.addTradeAgreement(new TradeAgreement(iron, planet2));
		planet1.addTradeAgreement(new TradeAgreement(iron, planet3));
		planet2.addTradeAgreement(new TradeAgreement(steel, planet1));
		
		stage = new GameStage(new ScalingViewport(Scaling.fill, 637.5f, 500f), data);
	    Gdx.input.setInputProcessor(stage);
	    
	    stage.addPlanet(planet1, Color.GREEN, 0, 0, 50);
	    stage.addPlanet(planet2, Color.RED, 200, 200, 50);
	    stage.addPlanet(planet3, Color.ORANGE, 500, 500, 200);
	    
//	    stage.getOverlay().showPlanetOverlay(planet1);
	    
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}
	
	public void dispose(){
		stage.dispose();
	}
}
