package sk.engine.vector;

public class Vector3f {
	
	public static final int LENGTH = 3;
	
	public static final int SIZE = Float.SIZE / 8 * LENGTH;
	
	public float x, y, z;
	
	public Vector3f() {
		this(0, 0, 0);
	}
	
	public Vector3f(Vector3f vec3) {
		this(vec3.x, vec3.y, vec3.y);
	}
	
	public Vector3f(Vector2f vec2, float z) {
		this(vec2.x, vec2.y, z);
	}
	
	public Vector3f(Vector2f vec2) {
		this(vec2.x, vec2.y);
	}
	
	public Vector3f(float x) {
		this(x, 0);
	}
	
	public Vector3f(float x, float y) {
		this(x, y, 0);
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getLength() {
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	public Vector3f normalize() {
		float length = getLength();
		x /= length;
		y /= length;
		z /= length;
		
		return this;
	}
	
	public float[] getData() {
		return new float[]{x, y, z};
	}
}