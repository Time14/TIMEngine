package sk.engine.physics.collision;

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
		
		double k = deltaY / deltaX;
		double m = point1.y - (k * point1.x);
//		Error correction if the slope is 0 or infinity
		if(k == 0) {
			k = Double.MIN_VALUE;
		}
//		Don't know if this is needed... (Infinity checks)
		/*
		else if(k == Double.POSITIVE_INFINITY) {
			k = 10000000;
		}
		else if(k ==  Double.NEGATIVE_INFINITY) {
			k = -10000000;
		}*/
		System.out.println(k);
		if((pointToTest.y - m) / k > pointToTest.x ) {
			return 1;
		}
		return 0;
	}
	
	private boolean isOdd(int i) {
		return i % 2 == 1;
	}	
}