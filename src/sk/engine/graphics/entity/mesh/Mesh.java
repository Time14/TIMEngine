package sk.engine.graphics.entity.mesh;

import java.io.ObjectInputStream.GetField;

import sk.engine.graphics.entity.mesh.vertex.Vertex;
import sk.engine.graphics.entity.mesh.vertex.Vertex2D;
import sk.engine.util.BufferUtil;
import sk.engine.util.GLUtil;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

public class Mesh {
	
	private Vertex[] vertices;
	
	private int vbo;
	
	private int offset;
	
	private int count;
	
	private int type;
	
	public Mesh() {
		
	}
	
	public Mesh setOffset(int offset) {
		this.offset = offset;
		
		return this;
	}
	
	public Mesh setCount(int count) {
		this.count = count;
		
		return this;
	}
	
	public Mesh addVertices(Vertex2D[] vertices) {
		return addVertices(GLUtil.QUADS, vertices);
	}
	
	public Mesh addVertices(int type, Vertex2D[] vertices) {
		this.vertices = vertices;
		this.type = type;
		
		offset = 0;
		
		count = vertices.length;
		
		vbo = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		glBufferData(GL_ARRAY_BUFFER, BufferUtil.toFloatBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		return this;
	}
	
	public void draw() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		int buffer_offset = 0;
		for(int i = 0; i < vertices[0].getComponents().length; i++) {
			glEnableVertexAttribArray(i);
			glVertexAttribPointer(i, vertices[0].getComponents()[i], GL_FLOAT, false, vertices[0].getSize(), buffer_offset);
			buffer_offset += vertices[0].getComponents()[i] * Float.SIZE / 8;
		}
		
		glDrawArrays(type, offset, count);
	}
	
	public Vertex[] getVertices() {
		return vertices;
	}
	
	public void destroy() {
		glDeleteBuffers(vbo);
	}
}