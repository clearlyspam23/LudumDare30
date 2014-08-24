package com.clearlyspam23.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.clearlyspam23.game.model.GameData;
import com.clearlyspam23.game.model.Ship;

public class ShipActor extends Group {
	
	private static final float pOffX = 2.5f;
	private static final float pOffY = 0;
	
	private TextureRegion base;
	private TextureRegion overlay;
	private Ship ship;
	private GameData data;
	private PooledEffect particles;
	private float poffx;
	private float poffy;
	
	public ShipActor(TextureRegion base, TextureRegion overlay, PooledEffect particles, float startX, float startY, float endX, float endY, Ship ship, GameData data){
		this.base = base;
		this.overlay = overlay;
		this.ship = ship;
		this.particles = particles;
		particles.start();
		this.setPosition(startX, startY);
		addAction(Actions.moveTo(endX, endY, ship.totalDistance*GameStage.TICK_RATE));
		this.data = data;
		this.setOrigin(5, 10);
		setSize(10, 20);
		float deltaX = endX - startX;
		float deltaY = endY - startY;
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX))-90;
		double cos = Math.cos(Math.toRadians(angle));
		double sin = Math.sin(Math.toRadians(angle));
		this.setRotation((float) angle);
		poffx = (float)(pOffX*cos - pOffY*sin);
		poffy = (float)(pOffX*sin + pOffY*cos);
		System.out.println(poffx + ", " + poffy);
		for (int i = 0; i < particles.getEmitters().size; i++) { //get the list of emitters - things that emit particles
            particles.getEmitters().get(i).getAngle().setLow((float) angle-90); //low is the minimum rotation
            particles.getEmitters().get(i).getAngle().setHigh((float) angle-90); //high is the max rotation
         }
	}
	
	public void act(float delta){
		super.act(delta);
		particles.setPosition(getX()+getWidth()/2, getY()+getHeight()/2);
		particles.update(delta);
		if(!data.getActiveShips().contains(ship)){
			remove();
			particles.free();
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		batch.setColor(getColor());
		batch.draw(base, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, getRotation());
		batch.setColor(Color.WHITE);
		batch.draw(overlay, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, getRotation());
//		particles.setPosition(getX()+poffx, getY()+poffy);
//		particles.update(0);
		particles.draw(batch);
//		super.draw(batch, parentAlpha);
	}

}
