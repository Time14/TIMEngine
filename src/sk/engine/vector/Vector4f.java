package sk.engine.vector;

import sk.engine.graphics.entity.Transform;

public class Vector4f {
	
	public static final int LENGTH = 4;
	
	public static final int SIZE = Float.SIZE / 8 * LENGTH;
	
	public float x, y, z, w;
	
	public Vector4f() {
		this(0, 0, 0, 0);
	}
	
	public Vector4f(Vector4f vec4) {
		this(vec4.x, vec4.y, vec4.z, vec4.w);
	}
	
	public Vector4f(Vector2f vec2_1, Vector2f vec2_2) {
		this(vec2_1.x, vec2_1.y, vec2_2.x, vec2_2.y);
	}
	
	public Vector4f(Vector2f vec2) {
		this(vec2.x, vec2.y);
	}
	
	public Vector4f(Vector3f vec3) {
		this(vec3.x, vec3.y, vec3.z);
	}
	
	public Vector4f(float x) {
		this(x, 0);
	}
	
	public Vector4f(float x, float y) {
		this(x, y, 0);
	}
	
	public Vector4f(float x, float y, float z) {
		this(x, y, z, 0);
	}
	
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f multiply(Transform transform) {
		Matrix4f mat4 = transform.getMatrix();
		
		Vector4f vec4 = new Vector4f(this);
		//I keept the old Sporre code
		x = vec4.x * mat4.matrix[0 * 4 + 0/*1*/] + vec4.y * mat4.matrix[1 * 4 + 0] + vec4.z * mat4.matrix[2 * 4 + 0] + vec4.w * mat4.matrix[3 * 4 + 0];
		y = vec4.x * mat4.matrix[0 * 4 + 1/*2*/] + vec4.y * mat4.matrix[1 * 4 + 1] + vec4.z * mat4.matrix[2 * 4 + 1] + vec4.w * mat4.matrix[3 * 4 + 1];
		z = vec4.x * mat4.matrix[0 * 4 + 2/*3*/] + vec4.y * mat4.matrix[1 * 4 + 2] + vec4.z * mat4.matrix[2 * 4 + 2] + vec4.w * mat4.matrix[3 * 4 + 2];
		w = vec4.x * mat4.matrix[0 * 4 + 3/*4*/] + vec4.y * mat4.matrix[1 * 4 + 3] + vec4.z * mat4.matrix[2 * 4 + 3] + vec4.w * mat4.matrix[3 * 4 + 3];
		
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
	
	public static final Vector4f multiply(Vector4f vec4, Transform transform) {
		return new Vector4f(vec4).multiply(transform);
	}
}