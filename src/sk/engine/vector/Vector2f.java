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
	
	// multiplication, dot product, cross product, and rotation
	
	public Vector2f mult(float scalar) {
		return new Vector2f(x * scalar, y * scalar);
	}
	
	public Vector2f mult(double scalar) {
		return this.mult((float) scalar);
	}
	
	public Vector2f sub(Vector2f vector) {
		return new Vector2f(x - vector.x, y - vector.y);
	}
	
	public Vector2f add(Vector2f vector) {
		return new Vector2f(vector.x + x, vector.y + y);
	}
	
	public Vector2f rotate(float radians) {
		return new Vector2f((float)(x * Math.cos(radians) - y * Math.sin(radians)), 
				(float)(x * Math.sin(radians) + y * Math.cos(radians)));
	}
	
	public float dotProduct(Vector2f vector) {
		return x*vector.x + y*vector.y;
	}
	
	public Vector2f getNormal(Vector2f pos1, Vector2f pos2) {
		return getNormal(pos1.sub(pos2));
	}
	
	
	public Vector2f getNormal(Vector2f vector) {
		return new Vector2f(-vector.y,vector.x);
	}
	
	public Vector2f slide(Vector2f normal) {
		float angle = (float) Math.atan2(normal.y, normal.x);
		float length = this.getLength();
		return new Vector2f(normal.y, -normal.x).normalize().mult(length * Math.sin(angle));
	}
	
	public float[] getData() {
		return new float[]{x, y};
	}
}