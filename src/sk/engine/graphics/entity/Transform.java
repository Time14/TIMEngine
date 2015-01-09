package sk.engine.graphics.entity;

import sk.engine.vector.Matrix4f;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;

public class Transform {
	
	private float rotation;
	private Vector3f position;
	private Vector3f scale;
	
	public Transform() {
		this(new Vector3f());
	}
	
	public Transform(Vector3f position) {
		this(position, 0);
	}
	
	public Transform(Vector3f position, float rotation) {
		this(position, rotation, new Vector3f(1, 1, 1));
	}
	
	public Transform(Vector3f position, float rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Matrix4f getMatrix() {
		Matrix4f matrix = Matrix4f.IDENTITY().rotate(rotation);
		
		matrix.multiply(Matrix4f.IDENTITY().translate(position));
		
		
		return matrix;
	}
	
	public Transform rotate(double d) {
		rotation += d;
		
		return this;
	}
	
	public Transform translate(Vector2f translation) {
		return translate(new Vector3f(translation.x, translation.y, 0));
	}

	public Transform translate(Vector3f translation) {
		position.x += translation.x;
		position.y += translation.y;
		position.z += translation.z;
		
		return this;
	}
	
	public Transform setPosition(Vector2f position) {
		return setPosition(new Vector3f(position.x, position.y, this.position.z));
	}
	
	public Transform setPosition(Vector3f position) {
		this.position = position;
		
		return this;
	}
	
	public Transform setRotation(float rotation) {
		this.rotation = rotation;
		
		return this;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public float getRotation() {
		return rotation;
	}
}