package sk.engine.physics.collision;

import java.awt.Window;

import sk.engine.debug.Debug;
import sk.engine.graphics.Color;
import sk.engine.physics.RigidBody;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector4f;

public class ColliderLineCast extends Collider {
	
	public ColliderLineCast(Vector4f[] points) {
		super(points);
	}
	
	public LineCastHit isColliding(Collider c) {
		int intersections = 0;
		LineCastHit hit;
		
		for(int i = 0; i < points.length; i++) {
			intersections = 0;
			for(int j = 0; j < c.length; j++) {
				intersections += lineCastCheck(	new Vector4f(c.points[j]).multiply(c.transform), 
												new Vector4f(c.points[(j + 1) % c.length]).multiply(c.transform), 
												new Vector4f(points[i]).multiply(transform));
			}
			if(isOdd(intersections)) {
				return new LineCastHit(new Vector2f(), null, null, i, true);
			}
		}
		
		return new LineCastHit(new Vector2f(), null, null, -1, false);
	}
	
	public int lineCastCheck(Vector2f point1, Vector2f point2, Vector2f pointToTest) {
		return lineCastCheck(point1.to4D(), point2.to4D(), pointToTest.to4D());
	}
	
	public int lineCastCheck(Vector4f point1, Vector4f point2, Vector4f pointToTest) {

		double deltaX = point1.x - point2.x;
		double deltaY = point1.y - point2.y;
		double xCord = 0;
		double k = deltaY / deltaX;
		double m = point1.y - (k * point1.x);
			
		if(k == 0) {
			return 0;
		}
		
		if(k == Double.POSITIVE_INFINITY || k == -Double.POSITIVE_INFINITY) {
			if(point1.y < point2.y){
				return (point1.y < pointToTest.y && point2.y > pointToTest.y 
						&& point1.x > pointToTest.x) ?0:1;
			} else {
				return (point2.y < pointToTest.y && point1.y > pointToTest.y 
						&& point1.x > pointToTest.x) ?0:1;
			}
		}
		
		xCord = (pointToTest.y - m) / k;
		
		if(xCord > pointToTest.x) {
			if(point1.x > point2.x) 
				return (point1.x > xCord && point2.x < xCord) ?1:0;
			else 
				return (point2.x > xCord && point1.x < xCord) ?1:0;
		}
		return 0;
	}
	
	public LineCastHit rayCast(Line line, RigidBody target, float min, float max) {
		int length = target.getColliders().get(0).getPoints().length;
		Vector2f position = new Vector2f();
		int i = 0;
		boolean hit = false;
		
		for(int a = 0; i < length; i++) {
			position = line.getInter(new Line(target.getColliderPoint(i),target.getColliderPoint(i+1)));
			if(position.x != position.x) {
				System.out.println(line.getFormula());
			}
			if(min <= position.x && position.x <= max) {
				if(((target.getColliderPoint(i+1).x >= position.x && position.x >= target.getColliderPoint(i).x) || 
					  (target.getColliderPoint(i).x >= position.x && position.x >= target.getColliderPoint(i+1).x))) {
					
					hit = true;
					break;
				}
			}
		}		
		return new LineCastHit(position, null, target, i, hit);
	}
	
	public boolean isOdd(int a) {
		return a%2 == 1;
	}
	
	public void update(double tick) {}
}