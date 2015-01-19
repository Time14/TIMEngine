package sk.engine.physics;

import sk.engine.core.Time;
import sk.engine.debug.Debug;
import sk.engine.graphics.Color;

import java.util.ArrayList;

import sk.engine.graphics.entity.Transform;
import sk.engine.physics.collision.Collider;
import sk.engine.physics.collision.CollisionData;
import sk.engine.physics.collision.LineCastHit;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;
import sk.engine.vector.Vector4f;

public class RigidBody {
	
	private PhysicsEngine pe;
	
	private ArrayList<Collider> colliders;
	
	private Transform transform;
	
	private Vector2f direction;
	
	private float torque;
	
	private float magnitude;
	
	private float mass;
	
	private float iMass;
	
	private float bounce;
	
	private float friction;
	
	private float drag;
	
	private float inertia = -1;
	
	private boolean alive;
	
	private boolean isOverlapping;
	
	private RigidBody collidingBody;
	
	public RigidBody() {
		setMass(1.0f);
		setDrag(1f);
		setFriction(1f);
		setBounce(0.5f);
		direction = new Vector2f();
		colliders = new ArrayList<>();
		alive = true;
		isOverlapping = false;
	}
	
	public void update(double delta) {
		addForce(pe.getGravity());
		if (mass == 0) {
			return;
		}
		//Transformation
//		if(direction.x != direction.x) {
//			System.out.println("The direction is equal to NaN"); 
//			return;
//		}
		
		transform.translate(Vector2f.mult(magnitude * delta, direction));
//		transform.rotate(torque * delta);
		
		//Applying drag 
//		torque -= torque * drag * delta;
		magnitude -= magnitude * drag * delta; 
	}
	
	public RigidBody setOverlapping (boolean bool) {
		
		isOverlapping = bool;
		
		return this;
	}
	
	public RigidBody setOverlapping (boolean bool, RigidBody collidingBody) {
		isOverlapping = bool;
		this.collidingBody = collidingBody;
		
		return this;
	}
	
	public boolean getOverLapping () {
		return isOverlapping;
	}
	
	public RigidBody getOverlappingBody() {
		return (isOverlapping) ? null : collidingBody;
	}
	
	public CollisionData isColliding(RigidBody rb) {
		for(Collider c : colliders)
			
			for(Collider c2 : rb.colliders) {
				LineCastHit hit = c.isColliding(c2);
				if(hit.isHit())
					return new CollisionData(this, rb, hit, this.pe);
			}
		
		return new CollisionData();
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
	
	public RigidBody setForce(Vector2f force) {
		
		magnitude = 0;
		addForce(force);
		
		return this;
	}
	
	
	public Vector2f getForce() {
		return direction.clone().mult(mass * magnitude);
	}
	
	public float getTorque() {
		return torque;
	}
	public RigidBody setTorque(float newTorque) {
		torque = newTorque;
		
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
		if(magnitude == 0) {
			return 1;
		}
		return magnitude;
	}
	
	public RigidBody setMagnitude(float magnitude) {
		this.magnitude = magnitude;
		
		return this;
	}
	
	public float getMass() {
		return mass;
	}
	
	public float getInvertedMass() {
		return iMass;
	}
	
	public RigidBody setMass(float mass) {
		if(mass == 0) {
			this.mass = 0;
			this.iMass = 0;
			return this;
		}
		this.mass = mass;
		iMass = 1/mass;
		
		return this;
	}
	
	public RigidBody addTorque(double newTorque) {
		torque += newTorque;
		return this;
	}
	
	public ArrayList<Collider> getColliders() {
		return colliders;
	}
	
	public Vector2f getColliderPoint(int index) {
		return this.getColliders().get(0).getPoint(index);
	}
	
	public Vector4f[] getColliderPoints() {
		return this.getColliders().get(0).getPoints();
	}
	
	public boolean hasMomentum() {
		return (magnitude > 0.0f);
	}
	
	public boolean hasTorque() {
		return (Math.abs(torque) > 0.00001f);
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
	
	public RigidBody addForce(float x, float y) {
		
		addForce(new Vector2f(x,y));
		
		return this;
	}	
	
	public RigidBody addForce(Vector2f force) {
		
		if(iMass == 0 || Math.round(force.getLength()) == 0 ) {
			return this;
		}
		direction.mult(magnitude).add(force.clone().mult(iMass));
		magnitude = direction.getLength();
		if(direction.x == direction.y && direction.y == 0) {
			direction = new Vector2f(1,0);
		}
		direction.normalize();
		
		return this;
	}
	
	public RigidBody sendPhysicsEngine(PhysicsEngine pe) {
		this.pe = pe;
		
		return this;
	}
	
	public float getBounce() {
		return bounce;
	}
	
	public RigidBody setBounce(float bounce) {
		bounce = Math.abs(bounce) < 1 ? Math.abs(bounce) : 1;
		this.bounce = Math.abs(bounce);
		
		return this;
	}
	
	public float getFriction() {
		return friction;
	}
	
	public RigidBody setFriction(float friction) {
		friction = Math.abs(friction) < 1 ? Math.abs(friction) : 1;
		this.friction = Math.abs(friction);
		
		return this;
	}

	public RigidBody setVelocity(Vector2f vel) {
		this.magnitude = vel.getLength();
		this.direction = vel.normalize();
		
		return this;
	}
	
	public Vector2f getVelocity() {
		return Vector2f.mult(magnitude, direction);
	}
	
	public float getDrag() {
		return drag;
	}
	
	public void setDrag(float drag) {
		this.drag = drag;
	}
	
	public void calculateInertia() {
		Vector4f[] points = this.getColliderPoints();
		float sum = 0;
		for(int i = 0; i < points.length - 1; i++){
			sum += (points[i].x * points[i].x + points[i].y * points[i].y + points[i].x*points[i+1].x + points[i].y*points[i+1].y + points[i+1].x * points[i+1].x + points[i+1].y * points[i+1].y)*
					(points[i].x * points[i+1].y - points[i+1].x * points[i].y);
		}
		this.inertia = 1/((mass/6) * sum);
	}
	
	public boolean hasInertia() {
		return inertia != -1;
	}
	
	public float getX() {
		return transform.getPosition().x;
	}
	
	public float getY() {
		return transform.getPosition().y;
	}
	
	public float getRotation() {
		return transform.getRotation();
	}
	
	public RigidBody setX(float x) {
		transform.getPosition().x = x;
		return this;
	}
	
	public RigidBody setY(float y) {
		transform.getPosition().y = y;
		return this;
	}
	
	public Vector3f getPosition() {
		return transform.getPosition();
	}
	
	public RigidBody setRotation(float r) {
		transform.setRotation(r);
		return this;
	}
	
	public RigidBody setPosition(float x, float y) {
		setPosition(new Vector2f(x, y));
		return this;
	}
	
	public RigidBody setPosition(Vector3f vec3) {
		transform.setPosition(vec3);
		return this;
	}
	
	public RigidBody setPosition(Vector2f vec2) {
		transform.setPosition(vec2);
		return this;
	}
	
	public String toString() {
		return "RigidBody" + pe.getRigidBodyIndex(this);
	}
}