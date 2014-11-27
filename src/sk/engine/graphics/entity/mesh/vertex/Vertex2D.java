package sk.engine.graphics.entity.mesh.vertex;

import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;

public class Vertex2D extends Vertex {
	
	private Vector3f pos;
	
	private Vector2f texPos;
	
	public Vertex2D() {
		this(new Vector2f());
	}
	
	public Vertex2D(Vector2f pos) {
		this(pos, new Vector2f());
	}
	
	public Vertex2D(Vector2f pos, Vector2f texPos) {
		this(new Vector3f(pos, 0), texPos);
	}
	
	public Vertex2D(Vector3f pos, Vector2f texPos) {
		this.pos = pos;
		this.texPos = texPos;
	}
	
	public int[] getComponents() {
		return new int[]{3, 2};
	}
	
	public float[] getData() {
		return new float[]{pos.x, pos.y, pos.z, texPos.x, texPos.y};
	}
	
	public int getSize() {
		return pos.SIZE + texPos.SIZE;
	}
	
	public int getLength() {
		return pos.LENGTH + texPos.LENGTH;
	}
	
}