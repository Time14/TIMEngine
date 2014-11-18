package sk.engine.vector;

public class Vector2f {
	
	public static final int LENGTH = 2;
	
	public static final int SIZE = Float.SIZE / 8 * LENGTH;
	
	public float x, y;
	
	public Vector2f() {
		this(0, 0);
	}
	
	public Vector2f(Vector2f vec2) {
		this(vec2.x, vec2.y);
	}
	
	public Vector2f(float x) {
		this(x, 0);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getLength() {
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public Vector2f normalize() {
		float length = getLength();
		x /= length;
		y /= length;
		
		return this;
	}
	
	public float[] getData() {
		return new float[]{x, y};
	}
}