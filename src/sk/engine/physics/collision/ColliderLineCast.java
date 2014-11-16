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
		
		return false;
	}
	 /*
	  * NP Sporre
	  */
	public double line(Vector2f point1, Vector2f point2, Vector2f pointToTest) {
		double deltaX = point1.x - point2.x;
		double deltaY = point1.y - point2.y;
		double m = point1.y - (deltaY/deltaX * point1.x);
		if (deltaY == 0 && point1.x < pointToTest.x ) 
			return 1;
		else if (deltaX == 0 && pointToTest.y == point1.y) 
			return 1;
		else if ( (pointToTest.y - m)/( deltaY/deltaX) < pointToTest.x )
			return 1;
		else
			return 0;
	}
	
	private boolean isOdd(int i){
		return i % 2 == 1;
	}	
}