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
		
		for(int i = 0; i < points.length; i++) {
			intersections = 0;
			for(int j = 0; j < c.length; j++) {
				intersections += lineCastCheck(new Vector4f(c.points[j]).multiply(c.transform), new Vector4f(c.points[(j + 1) % c.length]).multiply(c.transform), new Vector4f(points[i]).multiply(transform));
			}
			if(isOdd(intersections)) {
				return true;
			}
		}
		//isColliding = isOdd(intersections);
		
//		return isOdd(intersections);
		return false;
	}
	
	public int lineCastCheck(Vector4f point1, Vector4f point2, Vector4f pointToTest) {
		double deltaX = point1.x - point2.x;
		double deltaY = point1.y - point2.y;
		System.out.println("Point 1: " + point1.x + "\t" + point1.y);
		System.out.println("Point 2: " + point2.x + "\t" + point2.y);
		System.out.println("Point to test: " + pointToTest.x + "\t" + pointToTest.y);
		
		double k = deltaY / deltaX;
		double m = point1.y - (k * point1.x);
		if(deltaY == 0 &&  pointToTest.x < point1.x && pointToTest.x > point2.x) {
			System.out.println(1);
			return 1;
		}
		else if (deltaX == 0 && pointToTest.y < point1.y && pointToTest.y > point2.y) {
			System.out.println(2);
			return 1;
		}
		else if((pointToTest.y - m) / k < pointToTest.x && ((pointToTest.y - m) / k < point1.x || (pointToTest.y - m) / k < point2.x)) {
			System.out.println(3);
			return 1;
		}
		else {
			System.out.println(4);
			return 0;
		}
	}
	
	private boolean isOdd(int i) {
		return i % 2 == 1;
	}	
}