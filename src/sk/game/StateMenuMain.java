package sk.game;

import java.util.ArrayList;
import java.util.Random;

import sk.engine.io.input.*;
import sk.engine.core.Time;
import sk.engine.gamestate.GameState;
import sk.engine.gamestate.GameStateManager;
import sk.engine.graphics.Window;
import sk.engine.graphics.animation.Animation;
import sk.engine.graphics.animation.AnimationLinear;
import sk.engine.graphics.entity.mesh.texture.DynamicTexture;
import sk.engine.graphics.entity.mesh.texture.SpriteSheet;
import sk.engine.graphics.entity.mesh.texture.Texture;
import sk.engine.vector.Vector2f;

public class StateMenuMain extends GameState {
	
	private World world;
	private Player player;
	
	public StateMenuMain(GameStateManager gsm) {
		super(gsm, 0);
	}
	
	public void init() {
		world = new World();
		world.populate();
		player = world.getPlayer();
	}
	
	public void checkMouse(int b, boolean p) {}
	
	public void checkKeyboard(int k, boolean p) {
		world.checkKeyboard(k, p);
		
	}
	
	public void update() {
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			world.addBall(new Ball(player.getX(), player.getY(), 10, 10, player.getTransform().getRotation(), 1));
		}
		world.update();
	}
	
	public void draw() {
		world.draw();
	}
	
	public void exit() {}
}