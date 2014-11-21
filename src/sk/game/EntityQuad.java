package sk.game;

import sk.engine.graphics.entity.Entity;
import sk.engine.graphics.entity.Transform;
import sk.engine.graphics.entity.mesh.MeshQuad;
import sk.engine.graphics.entity.mesh.texture.DynamicTexture;
import sk.engine.graphics.entity.mesh.texture.SpriteSheet;
import sk.engine.graphics.entity.mesh.texture.Texture;
import sk.engine.graphics.shader.OrthographicalShaderProgram;
import sk.engine.physics.RigidBody;
import sk.engine.physics.collision.ColliderLineCast;
import sk.engine.vector.Vector3f;
import sk.engine.vector.Vector4f;

public class EntityQuad extends Entity {
	
	public EntityQuad(float x, float y, float width, float height) {
		super(new MeshQuad(true, 0, 0, width, height), OrthographicalShaderProgram.INSTACE, new Transform(new Vector3f(x, y, 0)));
		
		texture = new DynamicTexture(new SpriteSheet(2, 1, 16, 16).loadTexture("texture/terrain/box.png"));
		
		rigidBody = new RigidBody().addColliders(new ColliderLineCast(new Vector4f[] {
				new Vector4f(-width / 2, -height / 2, 0, 1),
				new Vector4f(width / 2, -height / 2, 0, 1),
				new Vector4f(width / 2, height / 2, 0, 1),
				new Vector4f(-width / 2, height / 2, 0, 1)
		})).setTransform(transform);
		
	}
	
	public void update() {
		
	}
}