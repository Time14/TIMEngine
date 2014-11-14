package sk.game;

import sk.engine.core.Time;
import sk.engine.graphics.animation.Animation;
import sk.engine.graphics.animation.AnimationLinear;
import sk.engine.graphics.entity.Entity;
import sk.engine.graphics.entity.Transform;
import sk.engine.graphics.entity.mesh.MeshQuad;
import sk.engine.graphics.entity.mesh.texture.DynamicTexture;
import sk.engine.graphics.entity.mesh.texture.SpriteSheet;
import sk.engine.graphics.shader.OrthographicalShaderProgram;
import sk.engine.vector.Vector3f;

public class Coin extends Entity {
	
	private Animation animation;
	
	public Coin(float x, float y) {
		super(new MeshQuad(true, 0, 0, 32, 32), OrthographicalShaderProgram.INSTACE, new Transform(new Vector3f(x, y, 0)));
		texture = new DynamicTexture(new SpriteSheet(9, 1, 32, 32).loadTexture("texture/terrain/coin.png"));
		animation = new AnimationLinear(texture, .1);
	}
	
	public void update() {
		animation.update(Time.getDelta());
	}
}