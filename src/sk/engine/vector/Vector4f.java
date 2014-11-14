package sk.engine.vector;

public class Vector4f {
	
	public static final int LENGTH = 4;
	
	public static final int SIZE = Float.SIZE * 8 * LENGTH;
	
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