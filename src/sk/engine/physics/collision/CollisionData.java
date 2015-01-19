
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
	
	private float energyKeept = 0.99f;
	
	private Vector2f normal;
	
	private Line direction;
	
	private float radius1;
	private float radius2;
	
	private float depth;
	
	private int corner;
	
	public boolean isColliding = false;
	
	public CollisionData() {
		isColliding = false;
	}
	
	public CollisionData(RigidBody body1, RigidBody body2, LineCastHit hit, PhysicsEngine pe) {
		
		// If both bodies are static, no point.
		if(body1.getMass() == 0 && body2.getMass() == 0) 
			return;
		
//		clc = new ColliderLineCast(new Vector4f[1]);
		
		this.pe = pe;
		
//		isColliding = true;
//		
//		corner = hit.getCornerID();
		
		this.body1 = body1;
		this.body2 = body2;
			
		// Draw a line to the center of the polygon
//		direction = new Line(body1.getColliderPoint(corner), body1.getPosition().to2D());
		
//		// Draw a line tracing back to collision
//		direction = new Line(body1.getColliderPoint(corner), body1.getColliderPoint(corner).clone().sub(body1.getVelocity()));
//		
//		// See where the line intersects the polygon
//		if(body1.getX() < body1.getColliderPoint(corner).x)
//			this.hit = clc.rayCast(direction, body2, Float.NEGATIVE_INFINITY, body1.getColliderPoint(corner).x);
//		else 
//			this.hit = clc.rayCast(direction, body2, body1.getColliderPoint(corner).x, Float.POSITIVE_INFINITY);

		// Define the normal
		SATTest(body1, body2);
//		normal = body1.getTransform().getPosition().to2D().sub(this.hit.getPosition());
//		radius1 = body1.getTransform().getPosition().to2D().sub(this.hit.getPosition()).getLength();
//		radius2 = body2.getTransform().getPosition().to2D().sub(this.hit.getPosition()).getLength();
		pe.addCollision(this);
	}
	
	public void solve() {

		velCalc();
		move();
	}
	
	private void velCalc() {
//		pe.getCollisionIndex();
		Vector2f relVel = body1.getVelocity().clone().sub(body2.getVelocity());
		
		float velAlongNormal = relVel.dot(normal);
		System.out.println(velAlongNormal);
		
		if(velAlongNormal > 0 || (body1.getInvertedMass() + body2.getInvertedMass()) == 0) {
			return;
		}
		float e = Math.min(body1.getBounce(), body2.getBounce());
		float j = -(1 + e) * velAlongNormal;
		Vector2f impulse = normal.clone().mult(j);
		body1.addForce(impulse);
		body2.addForce(impulse.invert());
	}
	
	private void move() {
		float perc = 0.5f;
		if(body1.getMass() != 0) {
			body1.getTransform().translate(normal.clone().mult(perc * depth));
		}
		if(body2.getMass() != 0){
			body2.getTransform().translate(normal.clone().mult(-perc * depth));
		}
	}
	
	private void SATTest(RigidBody a, RigidBody b) {
		
		normal = a.getPosition().to2D().sub(b.getPosition().to2D());
				
		float aExt = Math.abs(a.getColliderPoint(0).x - a.getPosition().x);
		float bExt = Math.abs(b.getColliderPoint(0).x - b.getPosition().x);
		
		float xOver = aExt + bExt - Math.abs(normal.x);
		
		if(xOver > 0) {
			
			aExt = Math.abs(a.getColliderPoint(0).y - a.getPosition().y);
			bExt = Math.abs(b.getColliderPoint(0).y - b.getPosition().y);
			
			float yOver = aExt + bExt - Math.abs(normal.y);
			
			if(yOver > 0) {
				
				if(xOver < yOver) {
					depth = xOver;
					if(normal.x < 0) {
						normal.x = -1;
						normal.y = 0;
						return;
					}
					else {
						normal.x = 1;
						normal.y = 0;
						return;
					}
				}
				else {
					depth = yOver;
					if(normal.y < 0) {
						normal.y = -1;
						normal.x = 0;
						return;
					}
					else {
						normal.y = 1;
						normal.x = 0;
						return;
					}
				}
			}
		}
	}
}