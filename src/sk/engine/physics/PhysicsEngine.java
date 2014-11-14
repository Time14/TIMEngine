package sk.engine.physics;

import java.util.ArrayList;

import sk.engine.physics.collision.Collider;

public class PhysicsEngine {
	
	private ArrayList<Collider> colliders;
	
	public PhysicsEngine() {
		colliders = new ArrayList<>();
	}
}