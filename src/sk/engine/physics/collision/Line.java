package sk.engine.physics.collision;

import sk.engine.vector.Vector2f;

public class Line {
	//y = kx + m
	private float slope = 0;
	private float m = 0;
	
	public Line() {
		this(new Vector2f());
	}
	
	public Line(Vector2f dir) {
		this(dir,new Vector2f());
	}
	
	public Line(Vector2f point1, Vector2f point2) {
		
		float deltaX = point2.x - point1.x;
		float deltaY = point2.y - point1.y;
		
		if(deltaY == 0) {
			slope = 0;
		} else {
			slope = deltaY / deltaX;
		}
		
		m = point1.y - (slope * point1.x);
	}
	
	public float getSlope() {
		return slope;
	}
	
	public Line setSlope(float slope) {
		this.slope = slope;
		return this;
	}
	
	public float getMValue() {
		return m;
	}
	
	public float getInterX(Line a) {
		return (m - a.m) / (a.slope - slope);
	}
	
	public float getInterY(Line a) {
		float inter = getInterX(a);
		return getYCoordinate(inter);
	}
	
	public Vector2f getInter(Line a) {
		float x = getInterX(a);
		return new Vector2f(x,getYCoordinate(x));
	}
	
	public float getXCoordinate(float y) {
		return (y-slope)/m;
	}
	
	public float getYCoordinate(float x) {
		return slope * x + m;
	}
	
	public String getFormula() {
		return "y = " + slope + "x + " + m;
	}
}
