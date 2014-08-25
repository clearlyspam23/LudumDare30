package com.clearlyspam23.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Planet;
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.Resource.ResourceType;
import com.clearlyspam23.game.model.Structure;
import com.clearlyspam23.game.model.TradeAgreement;
import com.clearlyspam23.game.model.ValueTable;
import com.clearlyspam23.game.model.logging.ConsoleLogger;
import com.clearlyspam23.game.model.structures.BaseRefinery;
import com.clearlyspam23.game.model.structures.Factory;
import com.clearlyspam23.game.model.structures.Manufactory;
import com.clearlyspam23.game.model.structures.Mine;
import com.clearlyspam23.game.model.structures.Refinery;
import com.clearlyspam23.game.model.structures.SpacePort;
import com.clearlyspam23.game.view.GameStage;
import com.clearlyspam23.game.view.PurchaseMaker;
import com.clearlyspam23.game.view.StructureRender;
import com.clearlyspam23.game.view.StructureRenderFactory;
import com.clearlyspam23.game.view.srenderes.MinePurchaseRender;
import com.clearlyspam23.game.view.srenderes.MineUpgradeRender;
import com.clearlyspam23.game.view.srenderes.RefineryPurchaseRender;
import com.clearlyspam23.game.view.srenderes.RefineryUpgradeRender;
import com.clearlyspam23.game.view.srenderes.SpacePortPurchaseRender;
import com.clearlyspam23.game.view.srenderes.SpacePortUpgradeRender;

public class LD30SpaceGame extends ApplicationAdapter {
	
	GameStage stage;
	private Texture resourcesTexture;
	
	private Texture sideMenuBackground;
	private Texture buttonTexture;
	private Texture acceptDecline;
	private Texture acceptDeclineOverlay;
	private Texture smallButton;
	private Texture simpleShip;
	private Texture stars;
	
	public TextureRegion simpleShipRegion;
	
	public TextureRegionDrawable starsDrawable;
	
	public NinePatch backgroundPatch;
	public NinePatch reversePatch;
	
	public NinePatch upButtonPatch;
	public NinePatch downButtonPatch;
	
	public NinePatch greyBackground;
	
	public NinePatch smallButtonPatch;
	
	public TextureRegion accept;
	public TextureRegion decline;
	
	public TextureRegion acceptOverlay;
	public TextureRegion declineOverlay;
	
	private BitmapFont font;
	private BitmapFont small;
	
	public Skin skin;
	
	public GameData data;
	
	public TextureRegion money;
	
	public Map<Resource, TextureRegion> resourceIcons = new HashMap<Resource, TextureRegion>();
	public final Map<Class<? extends Structure>, StructureRenderFactory> passiveStructureMap = new HashMap<Class<? extends Structure>, StructureRenderFactory>();
	public final Map<Class<? extends Structure>, StructureRenderFactory> activeStructureMap = new HashMap<Class<? extends Structure>, StructureRenderFactory>();
	public final Map<Resource, Structure> allowanceMap = new HashMap<Resource, Structure>();
	
	public static String[] nameArray = {"Artemis", "Tao", "Socrates", "Agnes", 
		"Basilius", "Bion", "Charon", "Dion", "Draco", "Otho", "Petronius", "Naevius",
		"Titiana", "Tullius", "Valentinus"
	};
	
	public static int MAX_NUM = 20;
//	SpriteBatch batch;
	
	
	@Override
	public void create () {
		//stuff, shit
		resourcesTexture = new Texture("art/Resources.png");
		font = new BitmapFont(Gdx.files.internal("fonts/GameMedium.fnt"));
		small = new BitmapFont(Gdx.files.internal("fonts/GameSmall.fnt"));
		stars = new Texture("art/Stars.png");
		starsDrawable = new TextureRegionDrawable(new TextureRegion(stars));
		skin = new Skin();
		skin.add("default", new LabelStyle(font, Color.WHITE));
		skin.add("small", new LabelStyle(small, Color.WHITE));
		sideMenuBackground = new Texture("art/MenuBackground1.png");
		backgroundPatch = new NinePatch(sideMenuBackground, 10, 32-27, 10, 32-27);
		TextureRegion reverse = new TextureRegion(sideMenuBackground);
		reverse.flip(true, false);
		reversePatch = new NinePatch(reverse, 10, 32-27, 10, 32-27);
		
		buttonTexture = new Texture("art/SpaceButton.png");
		upButtonPatch = new NinePatch(new TextureRegion(buttonTexture, 0, 0, 32, 32), 8, 8, 8, 8);
		downButtonPatch = new NinePatch(new TextureRegion(buttonTexture, 32, 0, 32, 32), 8, 8, 8, 8);
		greyBackground = new NinePatch(new TextureRegion(buttonTexture, 0, 32, 32, 32), 8, 8, 8, 8);
		skin.add("default", new Button.ButtonStyle(new NinePatchDrawable(upButtonPatch), new NinePatchDrawable(downButtonPatch), null));
		
		acceptDecline = new Texture("art/AcceptDeclineButtons.png");
		accept = new TextureRegion(acceptDecline, 0, 0, 32, 32);
		decline = new TextureRegion(acceptDecline, 32, 0, 32, 32);
		
		acceptDeclineOverlay = new Texture("art/AcceptDeclineOverlay.png");
		acceptOverlay = new TextureRegion(acceptDeclineOverlay, 0, 0, 32, 32);
		declineOverlay = new TextureRegion(acceptDeclineOverlay, 32, 0, 32, 32);
		
		smallButton = new Texture("art/SpaceButtonSmall.png");
		smallButtonPatch = new NinePatch(smallButton, 6, 6, 6, 6);
		
		simpleShip = new Texture("art/SpaceShipCombined.png");
		simpleShipRegion = new TextureRegion(simpleShip);
		
		
		
		data = new GameData();
		data.logger = new ConsoleLogger();
		
		ValueTable baseTable = new ValueTable();
		
		DropTable<Resource> baseResources = new DropTable<Resource>();
		
		//the iron tree
		Resource iron = new Resource("Iron", ResourceType.unrefined);
		resourceIcons.put(iron, fastLoad(0, 0));
		baseTable.addResource(iron, 10);
		Resource steel = new Resource("Steel", ResourceType.refined);
		resourceIcons.put(steel, fastLoad(1, 0));
		baseTable.addResource(steel, 15);
		Resource plating = new Resource("Metal Plating", ResourceType.manufactured);
		resourceIcons.put(plating, fastLoad(2, 0));
		baseTable.addResource(plating, 20);
		Resource reinforcedPlating = new Resource("Reinforced Plating", ResourceType.artisan);
		resourceIcons.put(reinforcedPlating, fastLoad(3, 0));
		baseTable.addResource(reinforcedPlating, 30);
		
		//the cookie tree
		Resource dough = new Resource("Dough", ResourceType.unrefined);
		resourceIcons.put(dough, fastLoad(0, 1));
		baseTable.addResource(dough, 10);
		Resource cookie = new Resource("Cookie", ResourceType.refined);
		resourceIcons.put(cookie, fastLoad(1, 1));
		baseTable.addResource(dough, 25);
		Resource cookieClicker = new Resource("Addictive Video Game", ResourceType.manufactured);
		resourceIcons.put(cookieClicker, fastLoad(2, 1));
		baseTable.addResource(cookieClicker, 50);
		Resource evilClicker = new Resource("Very Addictive Video Game", ResourceType.artisan);
		resourceIcons.put(evilClicker, fastLoad(3, 1));
		baseTable.addResource(evilClicker, 75);
		
		//the xbone tree
		Resource kineticOre = new Resource("Kinetic Ore", ResourceType.unrefined);
		resourceIcons.put(kineticOre, fastLoad(4, 0));
		baseTable.addResource(kineticOre, 25);
		Resource kineticGems = new Resource("Kinetic Gem", ResourceType.refined);
		resourceIcons.put(kineticGems, fastLoad(5, 0));
		baseTable.addResource(kineticGems, 35);
		Resource kinect = new Resource("Sensor Network", ResourceType.manufactured);
		resourceIcons.put(kinect, fastLoad(6, 0));
		baseTable.addResource(kinect, 40);
		Resource kinectOne = new Resource("Sensor Network One", ResourceType.artisan);
		resourceIcons.put(kinectOne, fastLoad(7, 0));
		baseTable.addResource(kinectOne, 45);
		
		//the euphoric tree
		Resource neabarium = new Resource("Neabarium", ResourceType.unrefined);
		resourceIcons.put(neabarium, fastLoad(0, 2));
		baseTable.addResource(neabarium, 15);
		Resource refinedNeabarium = new Resource("Refined Neabarium", ResourceType.refined);
		resourceIcons.put(refinedNeabarium, fastLoad(1, 2));
		baseTable.addResource(refinedNeabarium, 20);
		Resource mtndew = new Resource("Energy Elixer", ResourceType.manufactured);
		resourceIcons.put(mtndew, fastLoad(2, 2));
		baseTable.addResource(mtndew, 35);
		Resource bahablast = new Resource("Euphoric Energy Elixer", ResourceType.artisan);
		resourceIcons.put(bahablast, fastLoad(3, 2));
		baseTable.addResource(bahablast, 45);
		
		baseResources.addEntry(iron, 35f);
		baseResources.addEntry(kineticGems, 25f);
		baseResources.addEntry(neabarium, 25f);
		baseResources.addEntry(dough, 15f);
		
		final TextureRegion mineIcon = fastLoad(0, 3);
		final TextureRegion refineryIcon = fastLoad(1, 3);
		final TextureRegion factoryIcon = fastLoad(2, 3);
		final TextureRegion manufactoryIcon = fastLoad(3, 3);
		final TextureRegion spacePortIcon = fastLoad(4, 3);
		
		money = fastLoad(0, 4);
		
		final List<TextureRegionDrawable> levelOverlays = new ArrayList<TextureRegionDrawable>();
		levelOverlays.add(new TextureRegionDrawable(fastLoad(7, 7)));
		levelOverlays.add(new TextureRegionDrawable(fastLoad(1, 4)));
		levelOverlays.add(new TextureRegionDrawable(fastLoad(2, 4)));
		
		final TextureRegionDrawable acceptOverlayDraw = new TextureRegionDrawable(acceptOverlay);
		
		//lets do structures now
		passiveStructureMap.put(Mine.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new MineUpgradeRender(new NinePatchDrawable(greyBackground), width, resourceIcons, mineIcon, ((Mine)structure), skin, smallButtonPatch, money, levelOverlays, maker);
			}
			
		});
		activeStructureMap.put(Mine.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new MinePurchaseRender(new NinePatchDrawable(upButtonPatch), width, resourceIcons, mineIcon, ((Mine)structure), skin, money, acceptOverlayDraw);
			}
			
		});
		
		passiveStructureMap.put(SpacePort.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new SpacePortUpgradeRender(new NinePatchDrawable(greyBackground), width, spacePortIcon, ((SpacePort)structure), skin, smallButtonPatch, money, levelOverlays, maker);
			}
			
		});
		activeStructureMap.put(SpacePort.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new SpacePortPurchaseRender(new NinePatchDrawable(upButtonPatch), width, spacePortIcon, ((SpacePort)structure), skin, money, acceptOverlayDraw);
			}
			
		});
		
		passiveStructureMap.put(Refinery.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new RefineryUpgradeRender(new NinePatchDrawable(greyBackground), width, resourceIcons, refineryIcon, ((BaseRefinery)structure), skin, smallButtonPatch, money, levelOverlays, maker);
			}
			
		});
		activeStructureMap.put(Refinery.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new RefineryPurchaseRender(new NinePatchDrawable(upButtonPatch), width, resourceIcons, refineryIcon, ((BaseRefinery)structure), skin, money, acceptOverlayDraw);
			}
			
		});
		
		passiveStructureMap.put(Factory.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new RefineryUpgradeRender(new NinePatchDrawable(greyBackground), width, resourceIcons, factoryIcon, ((BaseRefinery)structure), skin, smallButtonPatch, money, levelOverlays, maker);
			}
			
		});
		activeStructureMap.put(Factory.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new RefineryPurchaseRender(new NinePatchDrawable(upButtonPatch), width, resourceIcons, factoryIcon, ((BaseRefinery)structure), skin, money, acceptOverlayDraw);
			}
			
		});
		
		passiveStructureMap.put(Manufactory.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new RefineryUpgradeRender(new NinePatchDrawable(greyBackground), width, resourceIcons, manufactoryIcon, ((BaseRefinery)structure), skin, smallButtonPatch, money, levelOverlays, maker);
			}
			
		});
		activeStructureMap.put(Manufactory.class, new StructureRenderFactory(){
			@Override
			public StructureRender getRenderer(Structure structure, float width, PurchaseMaker maker) {
				return new RefineryPurchaseRender(new NinePatchDrawable(upButtonPatch), width, resourceIcons, manufactoryIcon, ((BaseRefinery)structure), skin, money, acceptOverlayDraw);
			}
			
		});
		//and now allowances
		allowanceMap.put(iron, new Refinery(iron, steel, 10, 20));
		allowanceMap.put(steel, new Factory(steel, plating, 20, 40));
		allowanceMap.put(plating, new Manufactory(plating, reinforcedPlating, 20, 20));
		
		allowanceMap.put(dough, new Refinery(dough, cookie, 10, 30));
		allowanceMap.put(cookie, new Factory(cookie, cookieClicker, 10, 40));
		allowanceMap.put(cookieClicker, new Manufactory(cookieClicker, evilClicker, 10, 50));
		
		allowanceMap.put(kineticOre, new Refinery(kineticOre, kineticGems, 20, 20));
		allowanceMap.put(kineticGems, new Factory(kineticGems, kinect, 10, 5));
		allowanceMap.put(kinect, new Manufactory(kinect, kinectOne, 10, 5));
		
		allowanceMap.put(neabarium, new Refinery(neabarium, refinedNeabarium, 20, 15));
		allowanceMap.put(refinedNeabarium, new Factory(refinedNeabarium, mtndew, 10, 10));
		allowanceMap.put(mtndew, new Manufactory(mtndew, bahablast, 10, 10));
		
		Planet planet1 = generatePlanet(baseResources, baseTable);
		data.addPlanet(planet1);
		if(planet1.getStructures().size()==1)
			planet1.addStructure(new Mine(iron, 10, 50));
		Planet planet2 = generatePlanet(baseResources, baseTable);
		data.addPlanet(planet2);
		data.getPlanetGrid().makeThatConnection(planet1, planet2, 20);
		Planet planet3 = generatePlanet(baseResources, baseTable);
		if(planet3.getStructures().size()==1)
			planet3.addStructure(new Mine(kineticOre, 10, 50));
		data.addPlanet(planet3);
		data.getPlanetGrid().makeThatConnection(planet2, planet3, 50);
		Planet planet4 = generatePlanet(baseResources, baseTable);
		data.addPlanet(planet4);
		data.getPlanetGrid().makeThatConnection(planet2, planet4, 40);
//		planet1.addTradeAgreement(new TradeAgreement(iron, planet2));
////		planet1.addTradeAgreement(new TradeAgreement(iron, planet3));
//		planet2.addTradeAgreement(new TradeAgreement(steel, planet1));
		
		stage = new GameStage(new ScalingViewport(Scaling.fill, 637.5f, 500f), data, this);
	    Gdx.input.setInputProcessor(stage);
	    
	    generatePlanetNode(planet1, 0, 0);
	    generatePlanetNode(planet2, 200, 200);
	    generatePlanetNode(planet3, 700, 400);
	    generatePlanetNode(planet4, 100, 600);
	    
//	    stage.addPlanet(planet1, Color.GREEN, 0, 0, 50);
//	    stage.addPlanet(planet2, Color.RED, 200, 200, 50);
//	    stage.addPlanet(planet3, Color.ORANGE, 500, 400, 200);
	    
//	    stage.getOverlay().showPlanetOverlay(planet1);
	    
	}
	
	private Color[] colors = new Color[]{
			Color.GREEN, Color.BLUE, Color.GREEN, Color.RED, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.GRAY
	};
	
	private void generatePlanetNode(Planet planet, float x, float y){
		stage.addPlanet(planet, colors[(int) (Math.random()*colors.length)], x, y, (float) (Math.random()*100+50));
	}
	
	private Planet generatePlanet(DropTable<Resource> baseResources, ValueTable baseTable){
		Planet output = new Planet(generateName(), baseTable, 1.0f, 1.0f, new SpacePort(10, 50));
		double rand = Math.random();
		List<Resource> resources = new ArrayList<Resource>();
		while(rand>0.3){
			Resource r = baseResources.getValue();
			if(resources.contains(r)){
				continue;
			}
			resources.add(r);
			output.addStructure(new Mine(r, 10, 50));
			rand-=0.3;
		}
		return output;
	}
	
	private String generateName(){
		return nameArray[(int)(nameArray.length*Math.random())] + " " + ((int)(Math.random()*MAX_NUM+1));
	}
	
	private TextureRegion fastLoad(int indexX, int indexY){
		return new TextureRegion(resourcesTexture, 32*indexX, 32*indexY, 32, 32);
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
