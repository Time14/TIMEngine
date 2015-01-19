
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
		
		clc = new ColliderLineCast(new Vector4f[1]);
		
		this.pe = pe;
		
		isColliding = true;
		
		corner = hit.getCornerID();
		
		this.body1 = body1;
		this.body2 = body2;
			
		// Draw a line to the center of the polygon
//		direction = new Line(body1.getColliderPoint(corner), body1.getPosition().to2D());
		
		// Draw a line tracing back to collision
		direction = new Line(body1.getColliderPoint(corner), body1.getColliderPoint(corner).clone().sub(body1.getVelocity()));
		
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
		
		

		if(Math.abs(normal.x) > Math.abs(normal.y)){
			normal = new Vector2f(normal.x, 0);
		}
		else {
			normal = new Vector2f(0, normal.y);
		}
		depth = normal.getLength();
		normal.normalize();
		System.out.println(normal);
		pe.addCollision(this);
	}
	
	public void solve() {

		velCalc();
		/*
		rV = a.vel:clone():sub(b.vel)
		velAlongNormal = rV:dot(n:clone())
		if(velAlongNormal < 0 or (a.getIMass() + b.getIMass()) == 0)then
			return
		end
		e = math.min(a.bounce, b.bounce)
		j = -(1 + e) * velAlongNormal
		j = j / (a.getIMass() + b.getIMass())

		impulse = n:clone():scale(j)
		a.vel:add(impulse:clone():scale(a.getIMass() * 1))
		b.vel:add(impulse:clone():scale(-b.getIMass() * 1))
		*/
		if(body1.getMass() != 0) {
			body1.getTransform().translate(normal.mult(0.2 * depth));
		}
		if(body2.getMass() != 0){
			body2.getTransform().translate(normal.mult(-0.2 * depth));
		}//*/
//		System.out.println(force);
//		body1.addForce(Vector2f.mult(totalMag * -body1.getBounce() * body1.getInvertedMass(), normal));
//		body2.addForce(Vector2f.mult(totalMag * body2.getBounce() * body2.getInvertedMass(), normal));
//		body1.addTorque(normal.dotProduct(normal.clone()) * body1.getInvertedMass() * -radius1);
//		body2.addTorque(normal.dotProduct(normal.clone()) * body2.getInvertedMass() * radius2);
		
	}
	
	private void velCalc() {
		pe.getCollisionIndex();
		
		Vector2f relVel = body1.getVelocity().clone().sub(body2.getVelocity());
		
		float velAlongNormal = relVel.dot(normal);
		
		if(velAlongNormal < 0 || (body1.getInvertedMass() + body2.getInvertedMass()) == 0) {
			return;
		}
		float e = Math.min(body1.getBounce(), body2.getBounce());
		float j = -(1 + e) * velAlongNormal;
		Vector2f impulse = normal.clone().mult(j);
		System.out.println("pre : " + body1.getPosition().to2D());
		body1.addForce(impulse);
		body2.addForce(impulse.invert());
		System.out.println("su : " + body1.getPosition().to2D());
	}
}