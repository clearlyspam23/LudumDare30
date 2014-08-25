package com.clearlyspam23.game.view.srenderes;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.clearlyspam23.game.model.structures.SpacePort;
import com.clearlyspam23.game.view.PurchaseMaker;

public class SpacePortUpgradeRender extends SpacePortRender {
	
	private NinePatchDrawable buttonDraw;
	private TextureRegionDrawable moneyDraw;
	private Image levelOverlay;
	private List<TextureRegionDrawable> overlayDrawables;
	private PurchaseMaker maker;
	
	private Button button;

	public SpacePortUpgradeRender(Drawable background, float width, TextureRegion structureIcon, SpacePort port, Skin skin,
			NinePatch smallButton, TextureRegion money, List<TextureRegionDrawable> overlayDrawables, PurchaseMaker maker) {
		super(background, width, structureIcon, port, skin);
		buttonDraw = new NinePatchDrawable(smallButton);
		moneyDraw = new TextureRegionDrawable(money);
		this.overlayDrawables = overlayDrawables;
		this.maker = maker;
		init(width);
	}
	
	@Override
	protected Actor getIcon() {
		Group group = (Group) super.getIcon();
		levelOverlay = new Image();
		levelOverlay.setSize(64, 64);
		setOverlay();
		group.addActor(levelOverlay);
		return group;
	}
	
	private void setOverlay(){
		levelOverlay.setDrawable(overlayDrawables.get(port.getLevel()));
	}
	
	public void setDisabled(boolean flag){
		super.setDisabled(flag);
		if(!flag)
			button.setColor(Color.WHITE);
		else
			button.setColor(Color.DARK_GRAY);
	}
	
	public void act(float delta){
		if(button!=null&&button.getStage()!=null)
			button.setDisabled(!maker.canMakePurchase(port.getNextUpgradeCost()));
	}

	@Override
	protected Actor getSubTitle(float width) {
		if(!port.canUpgrade())
			return null;
		button = new Button(buttonDraw){
			public void setDisabled(boolean flag){
				super.setDisabled(flag);
				if(!flag)
					button.setColor(Color.WHITE);
				else
					button.setColor(Color.DARK_GRAY);
			}
		};
		Label upgrade = new Label("Upgrade: ", skin, "small");
		button.add(upgrade);
		button.setSize(width, 32);
		Image image = new Image();
		image.setDrawable(moneyDraw);
		image.setSize(16, 16);
		button.add(image).size(16);
		final Label label = new Label(Integer.toString(port.getNextUpgradeCost()), skin, "small");
		button.add(label);
		button.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				if(!maker.canMakePurchase(port.getNextUpgradeCost()))
					return;
				maker.makePurchase(port.getNextUpgradeCost());
				port.incrementLevel();
				setOverlay();
				if(!port.canUpgrade())
					button.remove();
				else
					label.setText(Integer.toString(port.getNextUpgradeCost()));
			}
		});
		return button;
	}

}
