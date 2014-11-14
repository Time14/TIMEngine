package sk.engine.graphics.entity;

import sk.engine.graphics.entity.mesh.Mesh;
import sk.engine.graphics.entity.mesh.texture.DynamicTexture;
import sk.engine.graphics.shader.ShaderProgram;

public abstract class Entity {
	
	protected Mesh mesh;
	
	protected ShaderProgram program;
	
	protected Transform transform;
	
	protected DynamicTexture texture;
	
	protected boolean alive;
	
	public Entity(Mesh mesh, ShaderProgram program, Transform transform) {
		this.mesh = mesh;
		this.program = program;
		this.transform = transform;
		alive = true;
	}
	
	public void draw() {
		if(texture != null)
			texture.bind();
		program.bind();
		program.sendMatrix(transform.getMatrix());
		mesh.draw();
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void kill() {
		alive = false;
	}
}