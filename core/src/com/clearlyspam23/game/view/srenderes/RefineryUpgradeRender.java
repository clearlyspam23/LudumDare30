package com.clearlyspam23.game.view.srenderes;

import java.util.List;
import java.util.Map;

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
import com.clearlyspam23.game.model.Resource;
import com.clearlyspam23.game.model.structures.BaseRefinery;
import com.clearlyspam23.game.view.PurchaseMaker;

public class RefineryUpgradeRender extends RefineryRender {
	
	private NinePatchDrawable buttonDraw;
	private TextureRegionDrawable moneyDraw;
	private Image levelOverlay;
	private List<TextureRegionDrawable> overlayDrawables;
	private PurchaseMaker maker;
	
	private Button button;

	public RefineryUpgradeRender(Drawable background, float width,
			Map<Resource, TextureRegion> resourceMap,
			TextureRegion structureIcon, BaseRefinery refinery, Skin skin, NinePatch smallButton, TextureRegion money, List<TextureRegionDrawable> overlayDrawables, 
			PurchaseMaker maker) {
		super(background, width, resourceMap, structureIcon, refinery, skin);
		this.maker = maker;
		buttonDraw = new NinePatchDrawable(smallButton);
		moneyDraw = new TextureRegionDrawable(money);
		this.overlayDrawables = overlayDrawables;
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
		levelOverlay.setDrawable(overlayDrawables.get(refinery.getLevel()));
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
			button.setDisabled(!maker.canMakePurchase(refinery.getNextUpgradeCost()));
	}

	@Override
	protected Actor getSubTitle(float width) {
		if(!refinery.canUpgrade())
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
		button.setSize(width, 32);
		Label upgrade = new Label("Upgrade: ", skin, "small");
		button.add(upgrade);
		Image image = new Image();
		image.setDrawable(moneyDraw);
		image.setSize(16, 16);
		button.add(image).size(16);
		final Label label = new Label(Integer.toString(refinery.getNextUpgradeCost()), skin);
		button.add(label);
		button.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				if(!maker.canMakePurchase(refinery.getNextUpgradeCost()))
					return;
				maker.makePurchase(refinery.getNextUpgradeCost());
				refinery.incrementLevel();
				setOverlay();
				if(!refinery.canUpgrade())
					button.remove();
				else
					label.setText(Integer.toString(refinery.getNextUpgradeCost()));
			}
		});
		return button;
	}

}
