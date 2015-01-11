package sk.engine.world;

import sk.engine.graphics.entity.mesh.Mesh;
import sk.engine.graphics.entity.Transform;
import sk.engine.graphics.entity.mesh.texture.DynamicTexture;
import sk.engine.graphics.shader.ShaderProgram;
import sk.engine.physics.RigidBody;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;

public abstract class Entity {
	
	protected RigidBody rigidBody;
	
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
	
	public abstract void update();
	
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
	
	public RigidBody getRigidBody() {
		return rigidBody;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public DynamicTexture getTexture() {
		return texture;
	}
	
	public void kill() {
		alive = false;
	}
	
	public float getX() {
		return transform.getPosition().x;
	}
	
	public float getY() {
		return transform.getPosition().y;
	}
	
	public float getZ() {
		return transform.getPosition().z;
	}
	
	public float getRotation() {
		return transform.getRotation();
	}
	
	public Entity setX(float x) {
		transform.getPosition().x = x;
		return this;
	}
	
	public Entity setY(float y) {
		transform.getPosition().y = y;
		return this;
	}
	
	public Entity setZ(float z) {
		transform.getPosition().z = z;
		return this;
	}
	
	public Entity setRotation(float r) {
		transform.setRotation(r);
		return this;
	}
	
	public Entity setPosition(float x, float y) {
		setPosition(new Vector2f(x, y));
		return this;
	}
	
	public Entity setPosition(Vector3f vec3) {
		transform.setPosition(vec3);
		return this;
	}
	
	public Entity setPosition(Vector2f vec2) {
		transform.setPosition(vec2);
		return this;
	}
}