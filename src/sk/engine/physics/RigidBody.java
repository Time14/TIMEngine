package sk.engine.physics;

import java.util.ArrayList;

import sk.engine.graphics.entity.Transform;
import sk.engine.physics.collision.Collider;
import sk.engine.vector.Vector2f;

public class RigidBody {
	
	private PhysicsEngine pe;
	
	private ArrayList<Collider> colliders;
	
	private Transform transform;
	
	private Vector2f direction;
	
	private float magnitude;
	
	private float mass;
	
	private boolean alive;
	
	public RigidBody() {
		direction = new Vector2f();
		colliders = new ArrayList<>();
		alive = true;
	}
	
	public void update() {
		
	}
	
	public boolean isColliding(RigidBody rb) {
		for(Collider c : colliders)
			for(Collider c2 : rb.colliders)
				if(c.isColliding(c2))
					return true;
		
		return false;
	}
	
	public RigidBody addColliders(Collider... colliders) {
		for(Collider c : colliders)
			this.colliders.add(c);
		
		return this;
	}
	
	public RigidBody setTransform(Transform transform) {
		this.transform = transform;
		
		for(Collider c : colliders)
			c.sendTransform(transform);
		
		return this;
	}

	public Vector2f getDirection() {
		return direction;
	}
	
	public RigidBody setDirection(Vector2f direction) {
		this.direction = direction;
		
		return this;
	}
	
	public float getMagnitude() {
		return magnitude;
	}
	
	public RigidBody setMagnitude(float magnitude) {
		this.magnitude = magnitude;
		
		return this;
	}
	
	public float getMass() {
		return mass;
	}
	
	public RigidBody setMass(float mass) {
		this.mass = mass;
		
		return this;
	}
	
	public ArrayList<Collider> getColliders() {
		return colliders;
	}
	
	public boolean hasMagnitude() {
		return magnitude > 0;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void kill() {
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}
}