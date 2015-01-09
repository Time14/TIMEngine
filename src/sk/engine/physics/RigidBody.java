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
		friction = 1;
		setBounce(1);
		direction = new Vector2f();
		colliders = new ArrayList<>();
		alive = true;
		isOverlapping = false;
	}
	
	public void update(double delta) {
		if (mass == 0) {
			return;
		}
		//Transformation
//		if(direction.x != direction.x) {
//			System.out.println("The direction is equal to NaN"); 
//			return;
//		}
		transform.getPosition().add(direction.clone().mult(magnitude * iMass * Time.getDelta()).to3D()); 
		transform.rotate(torque * delta);
		
		//Applying drag 
		torque -= torque * drag * Time.getDelta() ;
		magnitude -= magnitude * drag * Time.getDelta(); 
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
	public RigidBody setForce(Vector2f v) {
		float scale = v.getLength();
		return setForce(v.normalize(), scale);
	}
	
	public RigidBody setForce(Vector2f vector, float scalar) {
		
		vector.normalize();
		
		direction.x = vector.x;
		direction.y = vector.y;
		
		magnitude = scalar;
		
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
	
	public void addTorque(double newTorque) {
		torque += newTorque * iMass; 
	}
	
	public ArrayList<Collider> getColliders() {
		return colliders;
	}
	
	public Vector2f getColliderPoint(int index) {
		return this.getColliders().get(0).getPoint(index);
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
		
		if(iMass == 0) {
			return this;
		}
		direction = direction.mult(magnitude).add(force.mult(iMass));
		magnitude = direction.getLength();
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
	
	public float getFriction() {
		return friction;
	}
	
	public RigidBody setFriction(float friction) {
		
		this.friction = friction;
		
		return this;
	}
	
	public RigidBody setBounce(float bounce) {
		
		this.bounce = -1 * Math.abs(bounce);
		
		return this;
	}
	
	public Vector2f getVelocity() {
		return direction.mult(magnitude);
	}
	
	public float getDrag() {
		return drag;
	}
	
	public void setDrag(float drag) {
		this.drag = drag;
	}
	
	public void calculateInertia() {
		Vector4f[] points = this.getColliders().get(0).getPoints();
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
	public String toString() {
		return "RigidBody" + pe.getRigidBodyIndex(this);
	}
}