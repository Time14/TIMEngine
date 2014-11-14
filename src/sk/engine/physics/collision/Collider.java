package sk.engine.physics.collision;

import java.util.ArrayList;

import sk.engine.graphics.entity.Transform;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector4f;

public abstract class Collider {
	
	protected Vector4f[] points;
	
	protected Transform transform;
	
	public final int length;
	
	public Collider(Vector4f[] points) {
		this(points, new Transform());
	}
	
	public Collider(Vector4f[] points, Transform transform) {
		this.points = points;
		this.transform = transform;
		length = points.length;
	}
	
	public void sendTransform(Transform transform) {
		this.transform = transform;
	}
	
	public abstract boolean isColliding(Collider c);
	public abstract void update(double tick);
	
	public Vector4f[] getPoints() {
		return points;
	}
}