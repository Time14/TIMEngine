/*
package sk.engine.physics.collision;

import sk.engine.physics.PhysicsEngine;
import sk.engine.physics.RigidBody;
import sk.engine.vector.Vector2f;

public class CollisionData {
	PhysicsEngine pe;
	int bodyId1 = -1;
	int bodyId2 = -2;
	float radius1;
	float radius2;
	Vector2f dir1 = new Vector2f(0,0);
	Vector2f dir2 = new Vector2f(0,0);
	Vector2f normal;
	
	public void resolveColltision() {
		
		RigidBody body1 = pe.getRigidBody(bodyId1);
		RigidBody body2 = pe.getRigidBody(bodyId2);
		
		if( body1.getDirection().x - body2.getDirection().x + body1.getDirection().y - body2.getDirection().y > 
				body1.getDirection().x + body1.getDirection().x - body2.getDirection().x + body2.getDirection().x + 
				body1.getDirection().y + body1.getDirection().y - body2.getDirection().y + body1.getDirection().y) {
			
			dir1 = new Vector2f(body1.getForce().x - body2.getForce().x, body1.getForce().y - body2.getForce().y);
			dir2 = new Vector2f(body2.getForce().x - body1.getForce().x, body2.getForce().y - body1.getForce().y);
			
			body1.setDirection(dir1.normalize());
			body1.setMagnitude(dir1.getLength());
			
			body2.setDirection(dir2.normalize());
			body2.setMagnitude(dir2.getLength());
			
			body1.setTorque(body1.getTorque() + body2.getMass() * body2.getTorque() + 
					radius1 * new Vector2f(normal.y, normal.x).getDotProduct(body2.getForce()));
			body2.setTorque(body2.getTorque() + body2.getMass() * body2.getTorque() + 
					radius2 * new Vector2f(normal.y, normal.x).getDotProduct(body1.getForce()));
		}
	}
	
	public void createCollision(RigidBody body1, RigidBody body2) {
		
		bodyId1 = pe.getRigidBodyIndex(body1);
		bodyId2 = pe.getRigidBodyIndex(body2);
		
		normal = new Vector2f(body1.getTransform().getPosition().x - body2.getTransform().getPosition().x, 
				body1.getTransform().getPosition().y - body2.getTransform().getPosition().y).normalize();
		radius1 = ;
		
	}
}*/
