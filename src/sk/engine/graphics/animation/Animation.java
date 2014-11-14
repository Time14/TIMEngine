package sk.engine.graphics.animation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import sk.engine.graphics.entity.mesh.texture.DynamicTexture;
import sk.engine.graphics.entity.mesh.texture.Texture;

public abstract class Animation {
	
	private ArrayList<Snap> snaps;
	
	private int currentFrame;
	private int currentAnimation;
	
	protected DynamicTexture texture;
	
	private double counter;
	
	public Animation(DynamicTexture texture) {
		this.texture = texture;
		snaps = new ArrayList<>();
		counter = 0;
		currentAnimation = 0;
		currentFrame = 0;
	}
	
	public void update(double tick) {
		counter += tick;
		if(counter >= snaps.get(currentFrame).duration) {
			counter = 0;
			if(currentFrame > snaps.size()-2)
				currentFrame = 0;
			else
				currentFrame++;
			
			texture.swapX(currentFrame);
		}
	}
	
	public Animation addSnap(double duration, int frame) {
		return addSnap(new Snap(duration, frame));
	}
	
	public void swap(int animation) {
		currentAnimation = animation;
		texture.swapY(currentAnimation);
	}
	
	public Animation addSnap(Snap snap) {
		snaps.add(snap);
		
		return this;
	}
	
	public Texture getTexture() {
		return texture.getTexture();
	}
	
	public int getCurrentAnimation() {
		return currentAnimation;
	}
	
}