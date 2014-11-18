package sk.engine.vector;

import sk.engine.graphics.entity.Transform;

public class Vector4f {
	
	public static final int LENGTH = 4;
	
	public static final int SIZE = Float.SIZE / 8 * LENGTH;
	
	public float x, y, z, w;
	
	public Vector4f() {
		this(0, 0, 0, 0);
	}
	
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f multiply(Transform transform) {
		Matrix4f mat4 = transform.getMatrix();
		
		x = x * mat4.matrix[0 * 4 + 0] + y * mat4.matrix[0 * 4 + 1] + z * mat4.matrix[0 * 4 + 2] + w * mat4.matrix[0 * 4 + 3];
		y = x * mat4.matrix[1 * 4 + 0] + y * mat4.matrix[1 * 4 + 1] + z * mat4.matrix[1 * 4 + 2] + w * mat4.matrix[1 * 4 + 3];
		z = x * mat4.matrix[2 * 4 + 0] + y * mat4.matrix[2 * 4 + 1] + z * mat4.matrix[2 * 4 + 2] + w * mat4.matrix[2 * 4 + 3];
		w = x * mat4.matrix[3 * 4 + 0] + y * mat4.matrix[3 * 4 + 1] + z * mat4.matrix[3 * 4 + 2] + w * mat4.matrix[3 * 4 + 3];
		
		return this;
	}
	
	public float getLength() {
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	public Vector4f normalize() {
		float length = getLength();
		x /= length;
		y /= length;
		z /= length;
		
		return this;
	}
	
	public float[] getData() {
		return new float[]{x, y, z, w};
	}
	
}