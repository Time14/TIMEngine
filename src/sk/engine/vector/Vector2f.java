package sk.engine.vector;

import sk.engine.debug.Debug;
import sk.engine.graphics.Color;

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
		float length = (float)Math.sqrt(x * x + y * y);
		if(length != length)
			return 1;
		return length;
	}
	
	public Vector2f normalize() {
		float length = getLength();
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vector2f mult(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}
	
	public Vector2f mult(double scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}
	
	public Vector2f sub(Vector2f vector) {
		x -= vector.x;
		y -= vector.y;
		return this;
	}
	
	public Vector2f add(Vector2f vector) {
		x += vector.x;
		y += vector.y;
		return this;
	}
	
	public Vector2f rotate(float radians) {
		return this.rotate(radians, new Vector2f(0,0));
	}
	
	public Vector2f rotate(float radians, Vector2f center) {
	    float x = this.x - center.x;
	    float y = this.y - center.y;
	    this.x = center.x + (float) ((x * Math.cos(radians)) - (y * Math.sin(radians)));
	    this.y = center.y + (float) ((x * Math.sin(radians)) + (y * Math.cos(radians)));

	    return this;
	}
	
	public float dot(Vector2f vector) {
		return x*vector.x + y*vector.y;
	}
	
	public Vector2f getNormal() {
		return new Vector2f(-y,x);
	}
	
	public Vector2f invert() {
		x = -x;
		y = -y;
		
		return this;
	}
	
	public Vector2f slide(Vector2f normal) {
		float angle = (float) Math.atan2(normal.y, normal.x);
		float length = this.getLength();
		return new Vector2f(normal.y, -normal.x).normalize().mult(length * Math.sin(angle));
	}
	
	public Vector2f getPerp(Vector2f normal) {
		
		return this.getPara(normal.getNormal());
	}
	
	public Vector2f getPara(Vector2f normal) {

		float theta = normal.x/normal.getLength();
		
		return normal.clone().normalize().mult(theta * this.getLength());
	}
	public float[] getData() {
		return new float[]{x, y};
	}
	
	public Vector3f to3D() {
		return new Vector3f(x,y,0);
	}
	
	public String toString() {
		return "x : " + x + "\t y : " + y;
	}
	
	public Vector2f clone() {
		return new Vector2f(this);
	}
	
	public float cross(Vector2f vector) {
		return x*vector.y + y * vector.x;
	}
	
	public Vector4f to4D() {
		return new Vector4f(x,y,0,0);
	}
	
	public boolean isFacing(Vector2f point) {
		return isFacing(point, new Vector2f(0,0));
	}
	
	public boolean isFacing(Vector2f point, Vector2f origine) {
		Vector2f actualPoint = point.clone().sub(origine);
		
		Debug.drawLine(actualPoint, new Vector2f(), new Color(Color.RED));
		Debug.drawLine(this, new Vector2f(), new Color(Color.WHITE));
		
		if(point.x == x && point.y == 0) {
			return true;
		}
		else if(point.x == 0 || x == 0) {
			return actualPoint.y/Math.abs(actualPoint.y) == y/Math.abs(y);
		}
		else if(point.y == 0 || y == 0) {
			return actualPoint.x/Math.abs(actualPoint.x) == x/Math.abs(x);
		}
		return actualPoint.x/Math.abs(actualPoint.x) == x/Math.abs(x) && actualPoint.y/Math.abs(actualPoint.y) == y/Math.abs(y);
	}
	
	public double getAngle(Vector2f vector) {
		return Math.atan(this.getLength() / vector.getLength());
	}
	
	public static final Vector2f mult(float scalar, Vector2f vec2) {
		return new Vector2f(scalar * vec2.x, scalar * vec2.y);
	}
	
	public static final Vector2f mult(double scalar, Vector2f vec2) {
		return new Vector2f((float)scalar * vec2.x, (float)scalar * vec2.y);
	}
}