
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
	
	private RigidBody body1;
	private RigidBody body2;
	
	private float radius1;
	private float radius2;
	
	private float depth;
	private Vector2f normal;
	
	public boolean isColliding = false;
	
	public CollisionData() {
		isColliding = false;
	}
	
	public CollisionData(RigidBody body1, RigidBody body2, LineCastHit hit, PhysicsEngine pe) {
		
		if(body1.getMass() == 0 && body2.getMass() == 0) 
			return;
		
		this.pe = pe;
				
		this.body1 = body1;
		this.body2 = body2;

		simpleSATTest(body1, body2);
		System.out.println("Simple");
		System.out.println(depth);
		System.out.println(normal);
		SATTest(body1, body2);
		System.out.println("Wierd");
		System.out.println(depth);
		System.out.println(normal);
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
		
		if(velAlongNormal > 0 || (body1.getInvertedMass() + body2.getInvertedMass()) == 0) {
			return;
		}
		float e = Math.min(body1.getBounce(), body2.getBounce());
		float j = -(1 + e) * velAlongNormal;
		float f = Math.min(body1.getFriction(), body2.getFriction());
		Vector2f impulse = normal.clone().mult(j);
		body1.addImpulse(impulse);
		body2.addImpulse(impulse.invert());
		body1.setMagnitude(Math.max(body1.getMagnitude() - j * f , 0.0f));
		body2.setMagnitude(Math.max(body2.getMagnitude() - j * f , 0.0f));
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
	
	private void simpleSATTest(RigidBody a, RigidBody b) {
		
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
	
	private Vector2f project(RigidBody polygon,Vector2f normal) {
		float min = normal.dot(polygon.getColliderPoint(0));
		float max = min;
		for( int i = 0; i < polygon.getColliderPoints().length ;  i++) {
			float val = Math.abs(normal.dot(polygon.getPosition().to2D().sub(polygon.getColliderPoint(i))));
			if(val < min) {
				min = val;
			}
			if(max < val) {
				max = val;
			}
		}
		return new Vector2f(Math.abs(min), Math.abs(max));
	}
	
	private void SATTest(RigidBody a, RigidBody b) {
		
		Vector2f thisNormal = a.getTransform().getPosition().to2D();
		Vector2f aDim;
		Vector2f bDim;
		
		float distance;
		float over1;
		float over2;
		
		normal = new Vector2f(0,0);
		depth = thisNormal.getLength();
		
		for( int i = 0; i < a.getColliderPoints().length; i++) {
			thisNormal = a.getColliderPoint(i).sub(a.getColliderPoint((i+1) % a.getColliderPoints().length)).getNormal();
	 		thisNormal.normalize();
			aDim = project(a, thisNormal);
			bDim = project(b, thisNormal);
			distance = Math.abs(a.getPosition().to2D().sub(b.getPosition().to2D()).dot(thisNormal));
			over1 = (aDim.x + bDim.y) - distance;
			over2 = (aDim.y + bDim.x) - distance;
			if (0 < over1 || 0 < over2) {
				if ( over1 < over2 && 0 < over1) {
					if ( over1 < depth && 0 < over1) {
						depth = over1;
						normal = thisNormal;
					}
					continue;
				} else {
					if ( over2 < depth && 0 < over2) {
						depth = over2;
						normal = thisNormal;
					}
					continue;
				}
			}
		}
		for( int i = 0; i < b.getColliderPoints().length; i++) {
			thisNormal = b.getColliderPoint(i).sub(b.getColliderPoint((i+1) % b.getColliderPoints().length));
	 		thisNormal.normalize();
			aDim = project(a, thisNormal);
			bDim = project(b, thisNormal);
			distance = Math.abs(a.getPosition().to2D().sub(b.getPosition().to2D()).dot(thisNormal));
			over1 = (aDim.x + bDim.y) - distance;
			over2 = (aDim.y + bDim.x) - distance;
			if (0 < over1 || 0 < over2) {
				if ( over1 < over2) {
					if ( over1 < depth && 0 < over1) {
						depth = over1;
						normal = thisNormal;
					}
					continue;
				} else {
					if ( over2 < depth && 0 < over2) {
						depth = over2;
						normal = thisNormal;
					}
					continue;
				}
			}
		}
	}
}