package sk.game;

import sk.engine.core.Time;
import sk.engine.graphics.entity.Entity;
import sk.engine.graphics.entity.Transform;
import sk.engine.graphics.entity.mesh.MeshQuad;
import sk.engine.graphics.entity.mesh.texture.DynamicTexture;
import sk.engine.graphics.entity.mesh.texture.SpriteSheet;
import sk.engine.graphics.shader.OrthographicalShaderProgram;
import sk.engine.vector.Vector3f;

public class Ball extends Entity {
	
	private float speed;
	
	public Ball(float x, float y, float width, float height, float angle, float speed) {
		super(new MeshQuad(true, 0, 0, width, height), OrthographicalShaderProgram.INSTACE, new Transform(new Vector3f(x, y, 0)).setRotation(angle));
		texture = new DynamicTexture(new SpriteSheet(1, 1, 32, 32).loadTexture("texture/terrain/ball.png"));
		this.speed = speed;
	}
	
	public void update() {
		transform.getPosition().x += Time.getDelta() * speed * 300 * Math.sin(Math.toRadians(transform.getRotation()));
		transform.getPosition().y -= Time.getDelta() * speed * 300 * Math.cos(Math.toRadians(transform.getRotation()));
	}
}