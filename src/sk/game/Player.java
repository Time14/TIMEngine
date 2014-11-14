package sk.game;

import org.lwjgl.input.Keyboard;

import sk.engine.io.input.*;
import sk.engine.core.Time;
import sk.engine.graphics.animation.Animation;
import sk.engine.graphics.animation.AnimationLinear;
import sk.engine.graphics.entity.Entity;
import sk.engine.graphics.entity.Transform;
import sk.engine.graphics.entity.mesh.MeshQuad;
import sk.engine.graphics.entity.mesh.texture.DynamicTexture;
import sk.engine.graphics.entity.mesh.texture.SpriteSheet;
import sk.engine.graphics.shader.OrthographicalShaderProgram;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;

public class Player extends Entity {
	
	public static final float DEFAULT_SPEED = 1;
	
	private Animation animation;
	
	private float width, height, dx, dy, speed;
	
	private boolean moving;
	
	public Player(float x, float y, float width, float height) {
		super(new MeshQuad(true, 0, 0, width, height), OrthographicalShaderProgram.INSTACE, new Transform(new Vector3f(x, y, 0)));
		texture = new DynamicTexture(new SpriteSheet(6, 2, 128, 128).loadTexture("texture/terrain/player.png"));
		animation = new AnimationLinear(texture, .05);
		this.width = width;
		this.height = height;
		dx = 0;
		dy = 0;
		speed = DEFAULT_SPEED;
		moving = false;
	}
	
	public void checkInput(int k, boolean p) {
		if(p) {
			if(k == Keyboard.KEY_W) {
				dy = -speed;
				moving = true;
			} else if(k == Keyboard.KEY_A) {
				dx = -speed;
				moving = true;
			} else if(k == Keyboard.KEY_S) {
				dy = speed;
				moving = true;
			} else if(k == Keyboard.KEY_D) {
				dx = speed;
				moving = true;
			}
		} else {
			if(k == Keyboard.KEY_W) {
				if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
					dy = speed;
				} else {
					dy = 0;
				}
			} else if(k == Keyboard.KEY_S) {
				if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
					dy = -speed;
				} else {
					dy = 0;
				}
			} else if(k == Keyboard.KEY_A) {
				if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
					dx = speed;
				} else {
					dx = 0;
				}
			} else if(k == Keyboard.KEY_D) {
				if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
					dx = -speed;
				} else {
					dx = 0;
				}
				
				if(!areOtherMovementKeysDown(k))
					moving = false;
			}
		}
	}
	
	private boolean areOtherMovementKeysDown(int k) {
		switch(k) {
		default: return false;
		case Keyboard.KEY_W: return Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_D);
		case Keyboard.KEY_A: return Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_D);
		case Keyboard.KEY_S: return Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_D);
		case Keyboard.KEY_D: return Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_W);
		}
	}
	
	public void update() {
		animation.update(Time.getDelta());
		transform.getPosition().x += dx * Time.getDelta() * 300;
		transform.getPosition().y += dy * Time.getDelta() * 300;
		transform.setRotation(180-(float)Math.toDegrees(Math.atan2(Mouse.getX() - transform.getPosition().x, Mouse.getY() - transform.getPosition().y)));
		if(animation.getCurrentAnimation() == 1 && moving)
			animation.swap(0);
		else if(animation.getCurrentAnimation() == 0 && !moving)
			animation.swap(1);
	}
	
	public float getX() {
		return getTransform().getPosition().x;
	}
	
	public float getY() {
		return getTransform().getPosition().y;
	}
	
	public boolean isMoving() {
		return moving;
	}
}