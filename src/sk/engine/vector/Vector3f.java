package sk.engine.vector;

public class Vector3f {
	
	public static final int LENGTH = 3;
	
	public static final int SIZE = Float.BYTES * LENGTH;
	
	public float x, y, z;
	
	public Vector3f() {
		this(0, 0, 0);
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