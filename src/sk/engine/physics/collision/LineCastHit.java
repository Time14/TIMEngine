package sk.engine.physics.collision;

import sk.engine.physics.RigidBody;
import sk.engine.vector.Vector2f;

public class LineCastHit {

	private Vector2f position;
	private RigidBody source;
	private RigidBody target;
	private int cornerID;
	private boolean hit;
	
	public LineCastHit(Vector2f position, RigidBody source, RigidBody target, int cornerId, boolean hit) {
		this.position = position;
		this.source = source;
		this.target = target;
		this.cornerID = cornerId;
		this.hit = hit;
	}

	public Vector2f getPosition() {
		return position;
	}

	public RigidBody getSource() {
		return source;
	}

	public RigidBody getTarget() {
		return target;
	}

	public int getCornerID() {
		return cornerID;
	}

	public boolean isHit() {
		return hit;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setSource(RigidBody source) {
		this.source = source;
	}

	public void setTarget(RigidBody target) {
		this.target = target;
	}

	public void setCornerID(int cornerID) {
		this.cornerID = cornerID;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	
}
