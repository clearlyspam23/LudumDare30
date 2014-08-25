package com.clearlyspam23.game.view.srenderes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.clearlyspam23.game.model.structures.SpacePort;

public class SpacePortPurchaseRender extends SpacePortRender {
	
	private TextureRegionDrawable moneyDraw;
	private Image levelOverlay;
	private TextureRegionDrawable overlay;

	public SpacePortPurchaseRender(Drawable background, float width, TextureRegion structureIcon, 
			SpacePort port, Skin skin, 
			TextureRegion money, TextureRegionDrawable overlay) {
		super(background, width, structureIcon, port, skin);
		moneyDraw = new TextureRegionDrawable(money);
		this.overlay = overlay;
		init(width);
	}
	
	@Override
	protected Actor getIcon() {
		Group group = (Group) super.getIcon();
		levelOverlay = new Image();
		levelOverlay.setSize(64, 64);
		levelOverlay.setDrawable(overlay);
		levelOverlay.setVisible(false);
//		setOverlay();
		group.addActor(levelOverlay);
		return group;
	}
	
	public void setChecked(boolean flag){
		super.setChecked(flag);
		levelOverlay.setVisible(isChecked());
	}
	
//	private void setOverlay(){
//		levelOverlay.setDrawable(overlayDrawables.get(refinery.getLevel()));
//	}

	@Override
	protected Actor getSubTitle(float width) {
		final Table table = new Table();
		table.setSize(width, 32);
		Image image = new Image();
		image.setDrawable(moneyDraw);
		image.setSize(16, 16);
		table.add(image).size(16);
		final Label label = new Label(Integer.toString(port.getBuyingValue()), skin);
		table.add(label);
		return table;
	}

}
