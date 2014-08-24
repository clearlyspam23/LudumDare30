package com.clearlyspam23.game.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
	
	private PooledEffect effect;
	
	private float delta;
	
	public ParticleEffectActor(PooledEffect effect){
		this.effect = effect;
//		effect.setSize(100, 100);
//		effect.setPosition(120, 120);
//		effect.update(0);
//		effect.start();
	}
	
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		effect.setPosition(x, y);
//		effect.update(0);
	}
	
	public void act(float delta){
		this.delta = delta;
		effect.update(delta);
	}
	
	public void draw(Batch batch, float parentAlpha){
		effect.draw(batch);
	}

}
