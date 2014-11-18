package sk.game;

import java.util.ArrayList;
import java.util.Random;

import sk.engine.core.Time;
import sk.engine.graphics.Window;
import sk.engine.util.graph.Interpolation;

public class World {
	
	private Player player;
	private ArrayList<Ball> balls;
	private ArrayList<Coin> coins;
	private ArrayList<Ball> deadBalls;
	
	public World() {
		player = new Player(0, 0, 100, 100);
		coins = new ArrayList<>();
		balls = new ArrayList<>();
		deadBalls = new ArrayList<>();
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
		
		slerp.update(Time.getDelta());
		
		
		
		for(Ball b : balls) {
			b.update();
			if(b.getTransform().getPosition().x > Window.getWidth() || b.getTransform().getPosition().x < 0
					|| b.getTransform().getPosition().y > Window.getHeight() || b.getTransform().getPosition().y < 0)
				deadBalls.add(b);
		}
	}
	
	public void draw() {
		player.draw();
		for(Coin c : coins)
			c.draw();
		for(Ball b : balls) {
			b.draw();
		}
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