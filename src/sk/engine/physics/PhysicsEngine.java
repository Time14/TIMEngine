package sk.engine.physics;

import java.io.Console;
import java.util.ArrayList;
import java.util.Stack;

import sk.engine.physics.collision.Collider;
import sk.engine.physics.collision.CollisionData;
import sk.engine.vector.Vector2f;

public class PhysicsEngine {
	
	private int index = 0;
	private ArrayList<CollisionData> collisions;
	private ArrayList<RigidBody> bodies;
	private ArrayList<Integer> corners;
	private Stack<RigidBody> deathPool;
	
	private Vector2f gravity;
	
	private double currentDelta;
	
	public PhysicsEngine() {
		bodies = new ArrayList<>();
		deathPool = new Stack<>();
		collisions = new ArrayList<>();
		gravity = new Vector2f();
	}
	
	public void update(double tick) {
		
		currentDelta = tick;
		
		//Send dead rigid bodies to death pool
		for(RigidBody rb : deathPool) {
			rb.setOverlapping(false);
			if(bodies.contains(rb))
				bodies.remove(rb);
			
		}
		//Update the bodies if needed
		for(RigidBody rb : bodies) {
//			if(!rb.hasInertia()){
//				rb.calculateInertia();
//				System.out.println("What?!");
//			}
			if(rb.isAlive()) {
				if(rb.hasMomentum() || rb.hasTorque() || hasGravity()) {
					rb.update(tick);
				}
			} else {
				deathPool.add(rb);
			}
		}
		
		// Clear the collision cache
		collisions.removeAll(collisions);
		//Collision Check
		for(RigidBody rb1 :bodies) {
			for(RigidBody rb2 :bodies) {
				if(rb1 != rb2) {
					if(rb1.isColliding(rb2).isColliding == true) {
						rb1.setOverlapping(true, rb2);
						rb2.setOverlapping(true, rb1);
					}
				}
			}
		}
		
		//Resolve any collisions
		for(CollisionData col : collisions) {
			col.solve();
		}

	}
	
	public PhysicsEngine addRigidBody(RigidBody... bodies) {
		for(RigidBody rb : bodies)
			this.bodies.add(rb.sendPhysicsEngine(this));
		
		return this;
	}
	
	public RigidBody getRigidBody(int i) {
		return bodies.get(i);
	}
	
	public int getRigidBodyIndex(RigidBody rb) {
		return bodies.indexOf(rb);
	}
	
	public int getRigidBodyListLength() {
		return bodies.size();
	}
	
	public PhysicsEngine addCollision(CollisionData collision) {
		collisions.add(collision);
		
		return this;
	}
	
	public PhysicsEngine addCorner(int id) {
		corners.add(id);
		return this;
	}
	
	public int getCorner() {
		int val = corners.get(0);
		corners.remove(0);
		return val;
	}
	
	public void getCollisionIndex() {
		index ++;
		System.out.println(index);
	}
	
	public Vector2f getGravity() {
		return gravity;
	}
	
	public PhysicsEngine setGravity(float x, float y) {
		return setGravity(new Vector2f(x, y));
	}
	
	public PhysicsEngine setGravity(Vector2f vec2) {
		this.gravity = vec2;
		
		return this;
	}
	
	public boolean hasGravity() {
		return gravity.x != 0 || gravity.y != 0;
	}
	
	public double getCurrentDelta() {
		return currentDelta;
	}
}