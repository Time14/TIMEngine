package sk.engine.graphics.animation;

import sk.engine.graphics.entity.mesh.texture.DynamicTexture;

public class AnimationLinear extends Animation {
	
	private double duration;
	
	public AnimationLinear(DynamicTexture dt, double duration) {
		super(dt);
		this.duration = duration;
		
		registerSnaps();
	}
	
	public void registerSnaps() {
		for(int i = 0; i < texture.getSpriteSheet().getSpritesX(); i++) {
			addSnap(duration, i);
		}
	}
}