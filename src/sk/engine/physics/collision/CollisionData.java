package sk.engine.physics.collision;

import sk.engine.vector.Vector2f;

public class CollisionData {
	int bodyId1 = -1;
	int bodyId2 = -2;
	Vector2f contactPoint = new Vector2f(0,0);
	Vector2f normal = new Vector2f(0,0);
}
