package sk.engine.physics.collision;

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
		
		Vector4f[] internalPoints = this.points.clone();
		Vector4f[] externalPoints = c.points.clone();
		
		
		//TODO: We might want to remove the following two for loops for speed enhancement
		for(Vector4f vec4 : internalPoints)
			vec4.multiply(transform);
		
		for(Vector4f vec4 : externalPoints)
			vec4.multiply(c.transform);
		
		for(int i = 0; i < points.length; i++) {
			for(int j = 0; j < c.length; j++) {
				intersections += rayCastCheck(externalPoints[j], externalPoints[(j + 1) % c.length], internalPoints[i]);
			}
		}
		//isColliding = isOdd(intersections);
		return isOdd(intersections);
	}
	
	public int rayCastCheck(Vector4f point1, Vector4f point2, Vector4f pointToTest) {
		double deltaX = point1.x - point2.x;
		double deltaY = point1.y - point2.y;
		double k = deltaY / deltaX;
		double m = point1.y - (k * point1.x);
		if(deltaY == 0 && point1.x < pointToTest.x)
			return 1;
		else if (deltaX == 0 && pointToTest.y == point1.y) 
			return 1;
		else if((pointToTest.y - m) / k < pointToTest.x && ((pointToTest.y - m) / k < point1.x || (pointToTest.y - m) / k < point2.x))
			return 1;
		else
			return 0;
	}
	
	private boolean isOdd(int i){
		return i % 2 == 1;
	}	
}