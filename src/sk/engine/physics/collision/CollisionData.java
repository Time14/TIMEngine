
package sk.engine.physics.collision;

import java.nio.file.DirectoryStream;

import sk.engine.debug.Debug;
import sk.engine.graphics.Color;
import sk.engine.physics.PhysicsEngine;
import sk.engine.physics.RigidBody;
import sk.engine.vector.Vector4f;
import sk.engine.vector.Vector2f;
import sk.engine.physics.collision.ColliderLineCast;

public class CollisionData {
	
	private ColliderLineCast clc;
	
	private PhysicsEngine pe;
	
	private LineCastHit hit;
	
	private RigidBody body1;
	private RigidBody body2;
	
	private float energyKeept = 0.90f;
	
	private Vector2f normal;
	
	private Line direction;
	
	private float radius1;
	private float radius2;
	
	private int corner;
	
	public boolean isColliding = false;
	
	public CollisionData() {
		isColliding = false;
	}
	
	public CollisionData(RigidBody body1, RigidBody body2, LineCastHit hit, PhysicsEngine pe) {
		
		// If both bodies are static, no point.
		if(body1.getMass() == 0 && body2.getMass() == 0) 
			return;
		
		clc = new ColliderLineCast(new Vector4f[1]);
		
		this.pe = pe;
		
		isColliding = true;
		
		corner = hit.getCornerID();
		
		this.body1 = body1;
		this.body2 = body2;
			
		// Draw a line to the center of the polygon
		direction = new Line(body1.getColliderPoint(corner), body1.getPosition().to2D());
		
		// See where the line intersects the polygon
		if(body1.getX() < body1.getColliderPoint(corner).x)
			this.hit = clc.rayCast(direction, body2, Float.NEGATIVE_INFINITY, body1.getColliderPoint(corner).x);
		else 
			this.hit = clc.rayCast(direction, body2, body1.getColliderPoint(corner).x, Float.POSITIVE_INFINITY);

		// Define the normal
		normal = body1.getColliderPoint(hit.getCornerID()).sub(body2.getColliderPoint(this.hit.getCornerID() + 1)).getNormal();

//		if(normal.isFacing(body2.getTransform().getPosition().to2D(), this.hit.getPosition())) {
//			System.out.println("Some kind of hit");
//		} else {
//			normal = normal.normalize().invert();
//		}
		
//		normal = body1.getTransform().getPosition().to2D().sub(this.hit.getPosition());
		radius1 = body1.getTransform().getPosition().to2D().sub(this.hit.getPosition()).getLength();
		radius2 = body2.getTransform().getPosition().to2D().sub(this.hit.getPosition()).getLength();
		
		normal.normalize();
		
		pe.addCollision(this);
	}
	
	public void solve() {
		
		pe.getCollisionIndex();
		body1.isAlive();
		if(body1.getMass() > body2.getMass() && body1.getMass() != 0) {
			body1.getTransform().setPosition(body1.getTransform().getPosition().to2D().add(hit.getPosition().sub(body1.getColliderPoint(corner).mult(1.0001f))));
		}
		else if(body2.getMass() != 0){
			body2.getTransform().setPosition(body2.getTransform().getPosition().to2D().add(body1.getColliderPoint(corner).sub(hit.getPosition()).mult(1.0001f)));
		}
		
		float totalMag = body1.getMagnitude() * body1.getMass() + body2.getMagnitude() * body2.getMass();
		
		double angle1 = normal.getAngle(body1.getForce());
		double angle2 = normal.getAngle(body2.getForce());
		
		Vector2f force1 = new Vector2f().add(body2.getForce().mult( Math.cos(angle1) * body1.getBounce() ).add(body2.getForce().mult( Math.sin(angle1) * body1.getFriction() )));
		Vector2f force2 = new Vector2f().add(body1.getForce().mult( Math.cos(angle2) * body2.getBounce() ).add(body1.getForce().mult( Math.sin(angle2) * body2.getFriction() )));
		
		body1.addForce(force1);
		body2.addForce(force2);

		body1.addTorque(body1.getTransform().getPosition().to2D().add(hit.getPosition().sub(body1.getColliderPoint(corner))).cross(force1.clone().mult(body1.getInvertedMass()))/(radius1*radius1*body1.getMass())*-10);
		body2.addTorque(body2.getTransform().getPosition().to2D().add(hit.getPosition().sub(body2.getColliderPoint(corner))).cross(force2.clone().mult(body2.getInvertedMass()))/(radius2*radius2*body2.getMass())*10);

//		System.out.println(force);
//		body1.addForce(normal.clone().mult(totalMag * energyKeept * -body1.getBounce() * body1.getInvertedMass()));
//		body2.addForce(normal.clone().mult(totalMag * energyKeept *  body2.getBounce() * body2.getInvertedMass()));
//		body1.addTorque(body1.getTransform().getPosition().to2D().add(hit.getPosition().sub(body1.getColliderPoint(corner))).cross(normal.clone().mult(totalMag * energyKeept * -body1.getBounce() * body1.getInvertedMass()))/(radius1*radius1*body1.getMass())*-10);
//		body2.addTorque(body2.getTransform().getPosition().to2D().add(hit.getPosition().sub(body2.getColliderPoint(corner))).cross(normal.clone().mult(totalMag * energyKeept * -body2.getBounce() * body2.getInvertedMass()))/(radius2*radius2*body2.getMass())*10);

		
	}
}