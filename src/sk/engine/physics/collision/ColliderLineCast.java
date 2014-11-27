package sk.engine.physics.collision;

import sk.engine.debug.Debug;
import sk.engine.graphics.Color;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector4f;

public class ColliderLineCast extends Collider {
	
	public ColliderLineCast(Vector4f[] points) {
		super(points);
	}
	
	public void update(double tick) {
		
	}
	
	public boolean isColliding(Collider c) {
		int intersections = 0;
		//isColliding = false
		
		for(int i = 0; i < points.length; i++) {
			intersections = 0;
			for(int j = 0; j < c.length; j++) {
				intersections += lineCastCheck(	new Vector4f(c.points[j]).multiply(c.transform), 
												new Vector4f(c.points[(j + 1) % c.length]).multiply(c.transform), 
												new Vector4f(points[i]).multiply(transform));
			}
			if(isOdd(intersections)) {
				return true;
			}
		}
		
		return false;
	}
	
	public int lineCastCheck(Vector4f point1, Vector4f point2, Vector4f pointToTest) {
		
		double deltaX = point1.x - point2.x;
		double deltaY = point1.y - point2.y;
		double xCord = 0;
		double k = deltaY / deltaX;
		double m = point1.y - (k * point1.x);
//		Error correction if the slope is 0 or infinity
		if(k == 0) {
			k = 0.000000001f;
		}
		xCord = (pointToTest.y - m) / k;
		if(xCord > pointToTest.x) {
			if(point1.x > point2.x) {
				Debug.drawPoint(new Vector2f((float)xCord, pointToTest.y), new Color(new Vector4f(1,0,0,1), "ABGR"));
				return (point1.x > xCord && point2.x < xCord) ?1:0;
			}
			else {
				Debug.drawPoint(new Vector2f((float)xCord, pointToTest.y), new Color(new Vector4f(1,1,0,1), "ABGR"));
				return (point2.x > xCord && point1.x < xCord) ?1:0;
			}
		}
		return 0;
	}
	
	private boolean isOdd(int i) {
		return i % 2 == 1;
	}	
}