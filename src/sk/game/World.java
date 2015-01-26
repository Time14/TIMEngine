package sk.game;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import sk.engine.core.Time;
import sk.engine.debug.Debug;
import sk.engine.graphics.Color;
import sk.engine.graphics.Window;
import sk.engine.physics.PhysicsEngine;
import sk.engine.util.graph.Interpolation;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;
import sk.engine.vector.Vector4f;

public class World {
	
	private PhysicsEngine pe;
	
	private Player player;
	private ArrayList<Ball> balls;
	private ArrayList<Coin> coins;
	private ArrayList<Ball> deadBalls;
	
	private EntityQuad q1;
	private EntityQuad q2;
	private EntityQuad ground;
	
	private ArrayList<EntityQuad> quads;	
	
	Vector2f nor = new Vector2f(10,100);
	Vector2f force = new Vector2f(50,50);
	Vector2f offset = new Vector2f(100,100);
	
	public World() {
		player = new Player(0, 0, 100, 100);
		coins = new ArrayList<>();
		balls = new ArrayList<>();
		deadBalls = new ArrayList<>();
		quads = new ArrayList<>();
		pe = new PhysicsEngine().setGravity(0, 10);
		
		
		q2 = new EntityQuad(300, 300, 0, 100, 100);
		q1 = new EntityQuad(100, 100, 0, 100, 100);
		ground = new EntityQuad(300, 600, 0, 600, 100);
		
		ground.getRigidBody().setMass(0);
		ground.getRigidBody().setBounce(0.5f);
		ground.getTransform().setRotation(0);
		
		q2.getRigidBody().setMass(0f).setBounce(1.0f);
		q1.getRigidBody().setMass(0f).setBounce(1.0f);
		
		pe.addRigidBody(q1.getRigidBody());
		pe.addRigidBody(q2.getRigidBody());
		pe.addRigidBody(ground.getRigidBody());
		q1.getRigidBody().addTorque(-90);
		q2.getRigidBody().addTorque(90);
		q2.getRigidBody().addForce(new Vector2f(1f,1f));
		
		
		
		

		
	}
	
	public void checkMouse(int b, boolean p) {
		if(p) {
			if(b == 0) {
				quads.add(new EntityQuad(Mouse.getX(), Window.getHeight()-Mouse.getY(), 0, 50, 50));
				pe.addRigidBody(quads.get(quads.size()-1).getRigidBody().setBounce(0.0f).setMass(0.5f).addTorque(100));
			}
		}
	}
	
	public void checkKeyboard(int k, boolean p) {
		player.checkInput(k, p);
	}
	
	public World addBall(Ball ball) {
		balls.add(ball);
		
		return this;
	}
	
	private Interpolation slerp;
	private float speed = 1f;
	
	public void update() {
		
		Debug.drawLine(offset, offset.add(nor), new Color(Color.PURPLE));
		Debug.drawLine(offset, offset.add(force), new Color(Color.RED));
		
		pe.update(Time.getDelta() * speed);
//		Debug.drawLine(q1.getTransform().getPosition().to2D(), q2.getTransform().getPosition().to2D(), new Color(Color.GREEN));
		
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			speed -= Time.getDelta();
			if(speed < 0)
				speed = 0;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			speed += Time.getDelta();
		}
//		System.out.println(speed);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			q1.getRigidBody().addTorque((float)(1000 * Time.getDelta()));
//			q2.getRigidBody().addTorque((float)(1000 * Time.getDelta()));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			q1.getRigidBody().addTorque((float)(-1000 * Time.getDelta()));
//			q2.getRigidBody().addTorque((float)(-1000 * Time.getDelta()));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
			q1.getRigidBody().setMagnitude(0).setTorque(0).getTransform().setPosition(new Vector2f(75, 75)).setRotation(0.1f);
			q2.getRigidBody().setMagnitude(0).setTorque(0).getTransform().setPosition(new Vector2f(300, 300)).setRotation(0.1f);
		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			q1.getRigidBody().addForce((float)(-10.0f),(float)(0));
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			q1.getRigidBody().addForce((float)(10.0f),(float)(0));
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			q1.getRigidBody().addForce((float)(0),(float)(-50.0f));
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			q1.getRigidBody().addForce((float)(0),(float)(10.0f));
		}
		
//		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
//			q2.getRigidBody().addForce((float)(-10.0f),(float)(0));
//		}
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
//			q2.getRigidBody().addForce((float)(10.0f),(float)(0));
//		}
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
//			q2.getRigidBody().addForce((float)(0),(float)(-10.0f));
//		}
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
//			q2.getRigidBody().addForce((float)(0),(float)(10.0f));
//		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_M)) {
			q1.getRigidBody().setTorque(0).setMagnitude(0);
//			q2.getRigidBody().setTorque(0).setMagnitude(0);
		}
		
//		for(Ball b : deadBalls) {
//			balls.remove(b);
//		}
//		
//		deadBalls.clear();
//		
//		player.update();
//		
//		q1.update();
//		q2.update();
//		q1.getRigidBody().getTransform().getPosition().x += 10 * Time.getDelta();
//
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
//			q1.getTransform().rotate((float)Time.getDelta() * 10);
//		if(Keyboard.isKeyDown(Keyboard.KEY_E))
//			q2.getTransform().rotate((float)Time.getDelta() * 10);
//		
//		q2.getTransform().getPosition().x = Mouse.getX();
//		q2.getTransform().getPosition().y = -Mouse.getY() + Window.getHeight();
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
//			q1.getTransform().rotate((float)Time.getDelta() * 100);
//		}
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
//			q2.getTransform().rotate((float)Time.getDelta() * 100);
//		}
//		
//		if(q2.getRigidBody().isColliding(q1.getRigidBody())){
//			if(q1.getTexture().getSpriteX() == 0) {
//				q1.getTexture().swapX(1);
//				q2.getTexture().swapX(1);
//			}
//		} else {
//			if(q1.getTexture().getSpriteX() == 1) {
//				q1.getTexture().swapX(0);
//				q2.getTexture().swapX(0);
//			}
//		}
//			
//		coins.get(0).getTransform().getPosition().x += slerp.getSlerp() / 100;
//		for(Coin c : coins)
//			c.update();
//		
//		for(Ball b : balls) {
//			b.update();
//			if(b.getTransform().getPosition().x > Window.getWidth() || b.getTransform().getPosition().x < 0
//					|| b.getTransform().getPosition().y > Window.getHeight() || b.getTransform().getPosition().y < 0)
//				deadBalls.add(b);
//		}
	}
	
	public void draw() {
		q1.draw();
		q2.draw();
		ground.draw();
		
		for(EntityQuad q : quads) {
			q.draw();
		}
		/*
		player.draw();
		for(Coin c : coins)
			c.draw();
		for(Ball b : balls)
			b.draw();*/
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