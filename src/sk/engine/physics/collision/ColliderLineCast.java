package sk.engine.physics.collision;

import sk.engine.vector.Vector4f;

public class ColliderLineCast extends Collider {

	public ColliderLineCast(Vector4f[] points) {
		super(points);
	}
	
	public void update(double tick) {
		
	}
	
	public boolean isColliding(Collider c) {
		
		return false;
	}
	
}