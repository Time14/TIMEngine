package sk.game;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;

import sk.engine.core.Time;
import sk.engine.graphics.Window;
import sk.engine.io.input.Keyboard;
import sk.engine.util.graph.Interpolation;

public class World {
	
	private Player player;
	private ArrayList<Ball> balls;
	private ArrayList<Coin> coins;
	private ArrayList<Ball> deadBalls;
	
	private EntityQuad q1;
	private EntityQuad q2;
	
	public World() {
		player = new Player(0, 0, 100, 100);
		coins = new ArrayList<>();
		balls = new ArrayList<>();
		deadBalls = new ArrayList<>();
		q1 = new EntityQuad(300, 300, 100, 100);
		q2 = new EntityQuad(75, 75, 100, 100);
	}
	
	public void checkKeyboard(int k, boolean p) {
		player.checkInput(k, p);
	}
	
	public World addBall(Ball ball) {
		balls.add(ball);
		
		return this;
	}
	
	private Interpolation slerp;
	public void update() {
		
		for(Ball b : deadBalls) {
			balls.remove(b);
		}
		
		deadBalls.clear();
		
		player.update();
		
		q1.update();
		q2.update();
		
		q2.getTransform().getPosition().x = Mouse.getX();
		q2.getTransform().getPosition().y = -Mouse.getY() + Window.getHeight();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			q1.getTransform().rotate((float)Time.getDelta() * 100);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			q2.getTransform().rotate((float)Time.getDelta() * 100);
		}
		
		if(q2.getRigidBody().isColliding(q1.getRigidBody())){
			if(q1.getTexture().getSpriteX() == 0) {
				q1.getTexture().swapX(1);
				q2.getTexture().swapX(1);
			}
		} else {
			if(q1.getTexture().getSpriteX() == 1) {
				q1.getTexture().swapX(0);
				q2.getTexture().swapX(0);
			}
		}
			
		coins.get(0).getTransform().getPosition().x += slerp.getSlerp() / 100;
		for(Coin c : coins)
			c.update();
		
		for(Ball b : balls) {
			b.update();
			if(b.getTransform().getPosition().x > Window.getWidth() || b.getTransform().getPosition().x < 0
					|| b.getTransform().getPosition().y > Window.getHeight() || b.getTransform().getPosition().y < 0)
				deadBalls.add(b);
		}
	}
	
	public void draw() {
		q1.draw();
		q2.draw();
		player.draw();
		for(Coin c : coins)
			c.draw();
		for(Ball b : balls)
			b.draw();
	}
	
	public void populate() {
		slerp = new Interpolation(10);
		Random r = new Random();
		coins.add(new Coin(0, 0));
	}
	
	public Player getPlayer() {
		return player;
	}
}