package sk.engine.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import sk.engine.graphics.entity.mesh.vertex.Vertex;

public final class BufferUtil {
	
	public static final FloatBuffer toFloatBuffer(Vertex[] vertices) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * vertices[0].getLength());
		
		for(Vertex v : vertices) {
			buffer.put(v.getData());
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public static final FloatBuffer toFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static final IntBuffer toIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
}