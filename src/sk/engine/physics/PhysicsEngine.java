package sk.engine.physics;

import java.util.ArrayList;
import java.util.Stack;

import sk.engine.physics.collision.Collider;

public class PhysicsEngine {
	
	private ArrayList<RigidBody> bodies;
	private Stack<RigidBody> deathPool;
	
	
	public PhysicsEngine() {
		bodies = new ArrayList<>();
	}
	
	public void update(double tick) {
		//Send dead rigid bodies to death pool
		for(RigidBody rb : deathPool)
			if(bodies.contains(rb))
				bodies.remove(rb);
		
		//Update the bodies if needed
		for(RigidBody rb : bodies) {
			if(rb.isAlive()) {
				if(rb.hasMagnitude())
					rb.update();
			} else {
				deathPool.add(rb);
			}
		}
	}
	
	public PhysicsEngine addRigidBody(RigidBody... bodies) {
		for(RigidBody rb : bodies)
			this.bodies.add(rb);
		
		return this;
	}
	
}