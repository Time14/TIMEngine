package sk.engine.physics;

import sk.engine.vector.Vector2f;

public class RigidBody {
	
	private Vector2f direction;
	
	private float magnitude;
	
	private float mass;
	
	public RigidBody() {
		direction = new Vector2f();
	}
}